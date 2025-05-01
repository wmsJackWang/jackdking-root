package com.jackdking.sharding.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.jackdking.sharding.annotation.ShardingContext;
import com.jackdking.sharding.datasource.DynamicDataSourceHolder;
import com.jackdking.sharding.datasource.JDKingDynamicDataSource;
import com.jackdking.sharding.datasource.MasterWithManySlaverWrapper;
import com.jackdking.sharding.enums.DbShardingStrategyType;
import com.jackdking.sharding.properties.RwSeparationProperties;
import com.jackdking.sharding.properties.ShardingProperties;
import com.jackdking.sharding.strategy.rwseparation.RWSeparationStrategy;
import com.jackdking.sharding.strategy.rwseparation.RwSeparationRouteService;
import com.jackdking.sharding.strategy.rwseparation.SelfDefineStrategy;
import com.jackdking.sharding.strategy.rwseparation.impl.*;
import com.jackdking.sharding.strategy.sharding.*;
import com.jackdking.sharding.utils.ExpressionMethodArgsCalculateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.checkerframework.framework.qual.RequiresQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Configuration
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties({ShardingProperties.class, RwSeparationProperties.class})
public class JackdkingShardingJdbcAutoConfiguration {

    @Bean
    public ShardingRouteService shardingRouteService() {
        log.debug("init ShardingRouteService bean");
        return new ShardingRouteService();
    }

    @Bean(name = Constants.SEPARATION_CONTEXT_BEAN_NAME)
    public RwSeparationRouteService rwSeparationRouteService(ShardingProperties shardingProperties, RwSeparationProperties rwSeparationProperties) {
        log.debug("init RwSeparationRouteService bean");
        RWSeparationOnlyMasterStrategy rwSeparationOnlyMasterStrategy = new RWSeparationOnlyMasterStrategy(shardingProperties);
        RWSeparationWriteMasterReadMasterSlaveStrategy rwSeparationWriteMasterReadMasterSlaveStrategy = new RWSeparationWriteMasterReadMasterSlaveStrategy(shardingProperties);
        RWSeparationWriteMasterReadMonotonicSlaveStrategy rwSeparationWriteMasterReadMonotonicSlaveStrategy = new RWSeparationWriteMasterReadMonotonicSlaveStrategy(shardingProperties);
        RWSeparationWriteMasterReadSlaveStrategy rwSeparationWriteMasterReadSlaveStrategy = new RWSeparationWriteMasterReadSlaveStrategy(shardingProperties);

        List<SelfDefineStrategy> selfDefineStrategyList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rwSeparationProperties.getRwSeparationStrategyList())) {
            rwSeparationProperties.getRwSeparationStrategyList().stream()
                    .forEach(selfStrategyClassPath -> {
                        try {
                            SelfDefineStrategy instance = (SelfDefineStrategy) Class.forName(selfStrategyClassPath).newInstance();
                            selfDefineStrategyList.add(instance);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
        }
        RWSeparationSelfDefineStrategy rwSeparationSelfDefineStrategy = new RWSeparationSelfDefineStrategy(rwSeparationProperties, selfDefineStrategyList);
        List<RWSeparationStrategy> strategyList = new ArrayList<>();
        strategyList.add(rwSeparationOnlyMasterStrategy);
        strategyList.add(rwSeparationWriteMasterReadMasterSlaveStrategy);
        strategyList.add(rwSeparationWriteMasterReadMonotonicSlaveStrategy);
        strategyList.add(rwSeparationWriteMasterReadSlaveStrategy);
        strategyList.add(rwSeparationSelfDefineStrategy);

        RwSeparationRouteService rwSeparationRouteService = new RwSeparationRouteService(strategyList);
        return rwSeparationRouteService;
    }

    @Bean
    @Primary//指定在spring容器中只有一个datasource bean对象
    public DataSource dynamicDataSource(ShardingProperties shardingProperties, ShardingRouteService shardingRouteService) {
        log.info("shardingproperties:{}", JSON.toJSONString(shardingProperties));
        Map<Object, Object> dataSourceInsMap = new HashMap<>();
        Map<String/*数据源key*/, String/*数据源信息*/> dataSourceMap = shardingProperties.getDataSourceMap();
        JDKingDynamicDataSource dynamicDataSource = new JDKingDynamicDataSource();
        if (CollectionUtils.isEmpty(dataSourceMap)) {
            log.error("dataSource config is empty, plz ensure ds config right");
            throw new RuntimeException("dataSource config is empty");
        }
        DataSource ds = null;
        for (Map.Entry<String, String> dsEntry : dataSourceMap.entrySet()) {
            String val = dsEntry.getValue();
            String[] configs = val.split("\\|");
            ds = DataSourceBuilder.create().driverClassName(shardingProperties.getDriverClass()).url(configs[2])
                    .username(configs[0]).password(configs[1]).build();
            String dataSourceKey = dsEntry.getKey();
            dataSourceInsMap.put(dataSourceKey, ds);
        }
        dynamicDataSource.setTargetDataSources(dataSourceInsMap);

        // 默认分组数据源
        populateDefaultDataSourceGroup(shardingProperties, shardingRouteService);

        //所有数据源分组
        pupulateAllDataSourceGroup(shardingProperties, shardingRouteService);

        //表分片元数据
        populateTableShardingMetaData(shardingProperties, shardingRouteService);

        log.debug("shardingRouteService data:{}", JSON.toJSONString(shardingRouteService));

        return dynamicDataSource;
    }

    private void populateTableShardingMetaData(ShardingProperties shardingProperties, ShardingRouteService shardingRouteService) {
        Map<String/*逻辑表名*/, String/*分表元数据表达式*/> shardingMetaMap = shardingProperties.getShardingMetaMap();
        if (CollectionUtils.isEmpty(shardingMetaMap)) {
            log.error("shardingMetaMap config is empty, plz ensure ds config right");
            throw new RuntimeException("shardingMetaMap config is empty");
        }
        for (Map.Entry<String, String> shardingMetaEntry : shardingMetaMap.entrySet()) {

            String[] shardingEles = shardingMetaEntry.getValue().split("\\|");
            ShardingMeta meta = new ShardingMeta();
            meta.setLogicTableName(shardingMetaEntry.getKey());
            meta.setDbTotalCount(Integer.valueOf(shardingEles[1]));
            meta.setTableTotalCount(Integer.valueOf(shardingEles[2]));
            meta.setDbShardingStrategyType(DbShardingStrategyType.forValue(shardingEles[0]));
            ShardingStrategy strategy = shardingRouteService.decideDbShardingStrategy(meta.getDbShardingStrategyType());
            meta.setShardingStrategy(strategy);
            shardingRouteService.addShardingMeta(meta);
        }
    }


    private void pupulateAllDataSourceGroup(ShardingProperties shardingProperties, ShardingRouteService shardingRouteService) {
        List<DataSourceGroupMeta> allDataSourceGroup = new ArrayList<>();
        Map<String/** 组名 **/, Map<String/** 库序号 **/, String/** datasource key **/>> dbGroupDataSourceMap = shardingProperties.getDbGroupDataSourceMap();
        if (CollectionUtils.isEmpty(dbGroupDataSourceMap)) {
            log.error("dbGroupDataSourceMap config is empty, plz ensure ds config right");
            throw new RuntimeException("dbGroupDataSourceMap config is empty");
        }
        for (Map.Entry<String, Map<String, String>> entry : dbGroupDataSourceMap.entrySet()) {
            DataSourceGroupMeta groupMeta = new DataSourceGroupMeta();
            groupMeta.setGroupName(entry.getKey());

            Map<String, String> groupMetaDetail = entry.getValue();
            if (CollectionUtils.isEmpty(groupMetaDetail)) {
                log.error(String.format("dataSource Group:%s groupMetaDetail config is empty, plz ensure ds config right", entry.getKey()));
                throw new RuntimeException(String.format("dataSource Group:%s groupMetaDetail config is empty, plz ensure ds config right", entry.getKey()));
            }
            Map<String, MasterWithManySlaverWrapper> details = new HashMap<>();
            for (Map.Entry<String, String> metaDetail : groupMetaDetail.entrySet()) {
                String[] groupMetaInfoEles = metaDetail.getValue().split(",");
                String[] masterInfoEles = groupMetaInfoEles[0].split("\\|");
                MasterWithManySlaverWrapper wrapper = new MasterWithManySlaverWrapper();
                wrapper.setMasterDataSourceName(masterInfoEles[1]);//默认主库
                List<String> slaveDataSourceNameList = new ArrayList<>();
                for (int index = 1; index < groupMetaInfoEles.length; index++) {
                    slaveDataSourceNameList.add(groupMetaInfoEles[index].split("\\|")[1]);
                }
                wrapper.setSlaveDataSourceList(slaveDataSourceNameList);
                details.put(metaDetail.getKey(), wrapper);
            }
            groupMeta.setDetails(details);
            allDataSourceGroup.add(groupMeta);
            shardingRouteService.getShardingDataSourceConfig().setDbGroupDataSourceMap(allDataSourceGroup);
        }
    }

    private void populateDefaultDataSourceGroup(ShardingProperties shardingProperties, ShardingRouteService shardingRouteService) {
        DataSourceGroupMeta defaultGroupMeta = new DataSourceGroupMeta();
        defaultGroupMeta.setGroupName(Constants.DEFAULT_DATASOURCE_GROUP);
        String defaultGroupMetaInfo = shardingProperties.getDefaultDataSourceGroup();
        String[] defaultGroupMetaInfoEles = defaultGroupMetaInfo.split(",");
        String[] defaultMasterInfoEles = defaultGroupMetaInfoEles[0].split("\\|");
        MasterWithManySlaverWrapper wrapper = new MasterWithManySlaverWrapper();
        wrapper.setMasterDataSourceName(defaultMasterInfoEles[1]);//默认主库
        List<String> slaveDataSourceNameList = new ArrayList<>();
        for (int index = 1; index < defaultGroupMetaInfoEles.length; index++) {
            slaveDataSourceNameList.add(defaultGroupMetaInfoEles[index].split("\\|")[1]);
        }
        wrapper.setSlaveDataSourceList(slaveDataSourceNameList);
        Map<String, MasterWithManySlaverWrapper> defaultDetil = new HashMap<>();
        defaultDetil.put(Constants.DEFAULT_DATASOURCE_GROUP, wrapper);
        defaultGroupMeta.setDetails(defaultDetil);//默认数据源分组
        shardingRouteService.getShardingDataSourceConfig().setDefaultDataSourceGroup(defaultGroupMeta);

    }

    public static boolean isBlank(String str) {

        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim())) {
            return true;
        }
        return false;
    }

    @Around("@within(shardingContext)")
    public Object classIntercept(ProceedingJoinPoint joinPoint, ShardingContext shardingContext) throws Throwable {
        return proceedMethod(joinPoint, shardingContext);
    }

    @Around("@annotation(shardingContext)")
    public Object methodIntercept(ProceedingJoinPoint joinPoint, ShardingContext shardingContext) throws Throwable {
        return proceedMethod(joinPoint, shardingContext);
    }

    private Object proceedMethod(ProceedingJoinPoint joinPoint, ShardingContext shardingContext) throws Throwable {
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        String expression = shardingContext.monotonicPropertyExp();
        if (!org.apache.commons.lang3.StringUtils.isBlank(expression)) {

            Method method = resolveMethod(joinPoint);
            String val = ExpressionMethodArgsCalculateUtil.methodArgsExpressionCalculate(expression, method,
                    joinPoint.getArgs());
            DynamicDataSourceHolder.monotonicReadArgsHolder.set(val);
        }
        String shardingProperty = shardingContext.shardingProperty();
        Object obj = args[0];
        JSONObject json = JSON.parseObject(JSON.toJSONString(obj), JSONObject.class);
        String shardingPropertyVal = String.valueOf(json.get(shardingProperty));
        // 动态修改其参数
        // 注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        //保存注解信息
        DynamicDataSourceHolder.separationDBContextHolder.set(shardingContext);

        String[] logicTableNames = shardingContext.logicTableName();
        if (Objects.nonNull(logicTableNames) && logicTableNames.length > 1) {
            List<ShardingResult> shardingResults = new ArrayList<>();
            for (String logicTableName : logicTableNames) {
                String dbGroupKey = shardingContext.dbGroupKey();
                ShardingMeta tableShardingMeta = shardingRouteService.getShardingMetaList()
                        .stream()
                        .filter(shardingMeta -> org.apache.commons.lang3.StringUtils.equalsIgnoreCase(shardingMeta.getLogicTableName(), logicTableName))
                        .findFirst()
                        .get();
                DataSourceGroupMeta dataSourceGroupMeta = shardingRouteService.getShardingDataSourceConfig()
                        .getDbGroupDataSourceMap()
                        .stream()
                        .filter(groupMeta -> org.apache.commons.lang3.StringUtils.equalsIgnoreCase(dbGroupKey, groupMeta.getGroupName()))
                        .findFirst()
                        .get();
                ShardingStrategy strategy = shardingRouteService.decideDbShardingStrategy(tableShardingMeta.getDbShardingStrategyType());
                ShardingResult shardingResult = strategy.sharding(shardingPropertyVal, tableShardingMeta.getDbTotalCount(), tableShardingMeta.getTableTotalCount());
                shardingResult.setLogicTableName(logicTableName);
                shardingResults.add(shardingResult);
            }

            DynamicDataSourceHolder.shardingResultsHolder.set(shardingResults);
        }

        Object result = joinPoint.proceed(args);

        // 如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

    @Autowired
    ShardingRouteService shardingRouteService;

    private Method resolveMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        return getDeclaredMethodFor(targetClass, signature.getName(), signature.getMethod().getParameterTypes());
    }

    private Method getDeclaredMethodFor(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethodFor(superClass, name, parameterTypes);
            }
        }
        throw new IllegalStateException("Cannot resolve target method: " + name);
    }
}
