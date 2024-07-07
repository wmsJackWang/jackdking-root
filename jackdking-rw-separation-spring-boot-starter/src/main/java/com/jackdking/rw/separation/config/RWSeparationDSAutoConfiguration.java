package com.jackdking.rw.separation.config;


import com.google.common.collect.Maps;
import com.jackdking.rw.separation.annotation.RWSeparationDBContext;
import com.jackdking.rw.separation.datasource.DynamicDataSourceHolder;
import com.jackdking.rw.separation.datasource.JDKingDynamicDataSource;
import com.jackdking.rw.separation.datasource.MasterWithManySlaverWrapper;
import com.jackdking.rw.separation.enums.DatabaseMSPrefixType;
import com.jackdking.rw.separation.properties.RWSeparationDsProperties;
import com.jackdking.rw.separation.utils.ExpressionMethodArgsCalculateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Configuration
// 将 application.properties 的相关的属性字段与该类一一对应，并生成 Bean
@EnableConfigurationProperties(RWSeparationDsProperties.class)
public class RWSeparationDSAutoConfiguration {

    // 注入属性类
    @Autowired
    private RWSeparationDsProperties rwSeparationDsProperties;

    @Bean
    // 当容器没有这个 Bean 的时候才创建这个 Bean
//    @ConditionalOnMissingBean(PrintService.class)
    @Primary
    public DataSource dynamicDataSource(){
    	List<RWSeparationDsProperties.DsConfig> masterDsConfigs = rwSeparationDsProperties.getMasterDsConfigs();
        List<RWSeparationDsProperties.DsConfig> slaverDsConfigs = rwSeparationDsProperties.getSlaverDsConfigs();

        if (null == masterDsConfigs || masterDsConfigs.size() <= 0 || isBlank(rwSeparationDsProperties.getDefaultDs())) {
            throw new RuntimeException("数据源写库或默认库未配置，请正确配置数据源");
        }

    	DataSource ds = null;
    	Map<Object,Object> dataSourceMap = new HashMap<Object, Object>();
    	JDKingDynamicDataSource dynamicDataSource = new JDKingDynamicDataSource();
    	for(RWSeparationDsProperties.DsConfig dsConfig : masterDsConfigs){
    		ds = DataSourceBuilder.create()
    				.driverClassName(dsConfig.getDriverClassName())
    				.url(dsConfig.getJdbcUrl())
    				.username(dsConfig.getUsername())
    				.password(dsConfig.getPassword())
    				.build();
    		String dataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), dsConfig.getDsName());
    		dataSourceMap.put(dataSourceKey, ds);
            // 配置默认库
    		if(dsConfig.getDsName().equals(rwSeparationDsProperties.getDefaultDs())) {
                dynamicDataSource.setDefaultTargetDataSource(ds);
            }

            MasterWithManySlaverWrapper wrapper = new MasterWithManySlaverWrapper();
            wrapper.setMasterDatasource(ds);
            wrapper.setStringDataSourceMap(new HashMap<>());
            DynamicDataSourceHolder.addMasterSlave(dataSourceKey, wrapper);
    	}

    	if (!CollectionUtils.isEmpty(slaverDsConfigs)) {
            for(RWSeparationDsProperties.DsConfig dsConfig : slaverDsConfigs){
              ds = DataSourceBuilder.create()
                .driverClassName(dsConfig.getDriverClassName())
                .url(dsConfig.getJdbcUrl())
                .username(dsConfig.getUsername())
                .password(dsConfig.getPassword())
                .build();
              String slaveDataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.SLAVE.getPrefix(), dsConfig.getDsName());
              String masterDataSourceKey = String.format("%s:%s", DatabaseMSPrefixType.MASTER.getPrefix(), dsConfig.getMasterDsName());
              dataSourceMap.put(slaveDataSourceKey, ds);
              MasterWithManySlaverWrapper masterWithManySlaverWrapper = DynamicDataSourceHolder.getDsContext().get(masterDataSourceKey);
              if (Objects.isNull(masterWithManySlaverWrapper)) {
                  throw new RuntimeException(String.format("数据源写库%s未配置，请正确配置数据源", dsConfig.getMasterDsName()));
              }
              masterWithManySlaverWrapper.getStringDataSourceMap().put(slaveDataSourceKey, ds);
            }
      }

      dynamicDataSource.setTargetDataSources(dataSourceMap);
      return dynamicDataSource;
    }

    public static boolean isBlank(String str) {

        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim())) {
          return true;
        }
        return false;
    }

    @Around("@within(separationDBContext)")
    public Object classIntercept(ProceedingJoinPoint joinPoint, RWSeparationDBContext separationDBContext) throws Throwable {
        return proceedMethod(joinPoint, separationDBContext);
    }

    @Around("@annotation(separationDBContext)")
    public Object methodIntercept(ProceedingJoinPoint joinPoint, RWSeparationDBContext separationDBContext) throws Throwable {
        return proceedMethod(joinPoint, separationDBContext);
    }

    private Object proceedMethod(ProceedingJoinPoint joinPoint, RWSeparationDBContext separationDBContext) throws Throwable {
        //获取方法参数值数组
        Object[] args = joinPoint.getArgs();
        String expression = separationDBContext.monotonicPropertyExp();
        if (!org.apache.commons.lang3.StringUtils.isBlank(expression)) {

            Method method = resolveMethod(joinPoint);
            String val = ExpressionMethodArgsCalculateUtil.methodArgsExpressionCalculate(expression, method, joinPoint.getArgs());
            DynamicDataSourceHolder.monotonicReadArgsHolder.set(val);
        }
        //保存注解信息
        DynamicDataSourceHolder.separationDBContextHolder.set(separationDBContext);

        //动态修改其参数
        //注意，如果调用joinPoint.proceed()方法，则修改的参数值不会生效，必须调用joinPoint.proceed(Object[] args)
        Object result = joinPoint.proceed(args);

        //如果这里不返回result，则目标对象实际返回值会被置为null
        return result;
    }

    public static void main(String[] args) throws Exception {
        // 找到那个函数
        Method method = RWSeparationDSAutoConfiguration.class.getDeclaredMethod("dummy", int.class, Object.class);
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println("参数名为: " + parameter.getName());
        }
    }

    /**
     * 由于不关心这个函数里的逻辑, 此函数内就没有写任何代码
     * 两个参数都相当于占位符
     *
     * @param firstPlaceHolderWithALongName 就是个展示符, 名字很长, 容易被看到
     * @param secondPlaceHolder             也是一个占位符
     */
    private void dummy(int firstPlaceHolderWithALongName, Object secondPlaceHolder) {

    }

    private Object getFieldsNameV2(ProceedingJoinPoint joinPoint) {

        Map<String, Object> params = new HashMap<>();
        Object[] paramVals = joinPoint.getArgs();
        String[] paramNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();

        for (int index = 0 ; index < paramNames.length ; index++) {
            params.put(paramNames[index], paramVals[index]);
        }
        return params;
    }

    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", Integer.class);
            put("java.lang.Double", Double.class);
            put("java.lang.Float", Float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };
    //返回方法的参数名
    private static Map<String, Object> getFieldsName(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        Map<String, Object> name2ValMap = Maps.newHashMap();
        String classType = joinPoint.getSignature().getDeclaringType().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                //获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
//                Class s = map.get(result);
                classes[k] = args[k].getClass();
            }
        }
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        //获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Class<?> classInstance = Class.forName(classType);
        Method method = classInstance.getDeclaredMethod(methodName, classes);
        Parameter[] parameters = method.getParameters();

        for (int index = 0 ; index < parameters.length ; index++) {
            name2ValMap.put(parameters[index].getName(), args[index]);
        }

        return name2ValMap;
    }

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
