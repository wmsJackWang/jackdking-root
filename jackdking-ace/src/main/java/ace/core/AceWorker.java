package ace.core;

import ace.annoation.Attributor;
import ace.attributor.IAttributor;
import ace.classifier.IClassifier;
import ace.constant.Constants;
import ace.enums.ErrorMessageCode;
import ace.executor.IExecutor;
import ace.factory.AceFactory;
import ace.utils.AceUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Preconditions;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.invoke.ConstantCallSite;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class AceWorker {
    private final static AceWorker INSTANCE = new AceWorker();
    AceFactory aceFactory = AceFactory.getInstance();

    public static void main(String[] args) {
        Map<String,String> temp = Maps.newHashMap();
        temp.put("1","2");
        temp.put("2","2");
        temp.put("3","2");
        temp = temp.entrySet().stream().filter(e -> !e.getKey().equals("1")).collect(Collectors.toMap(e -> e.getKey(),e -> e.getValue()));
        System.out.println(JSON.toJSONString(temp));

    }

    private AceWorker(){

    }
    public static AceWorker getInstance() {
        return INSTANCE;
    }

    /*
     * 遍历所有的分类器，返回满足的分类器并获取分类器返回的执行器。
     */
    public AceResult classify(AceContext aceContext) throws Exception {
        Assert.notEmpty(aceContext.getAceScene().classifierList,"classifier collection can not be empty");
        ValidationClassifier(aceContext.getAceScene().classifierList);
        AceResult retResult = new AceResult();
        Map<String,AceResult> allClassifierResult;
        Map<String, AceResult> finalAllClassifierResult =  Maps.newHashMap();
        aceContext.getAceScene().classifierList.stream().forEach(classifierName -> {
            finalAllClassifierResult.put(classifierName,classify(classifierName,aceContext));
        });

        log.debug("all classifier result:{}",JSON.toJSONString(finalAllClassifierResult));
        allClassifierResult = finalAllClassifierResult.entrySet()
                .stream()
                .filter(classifierResult -> classifierResult.getValue().getIsEffect())
                .collect(Collectors.toMap(e -> e.getKey(),e -> e.getValue()));

        long classifierCount = allClassifierResult.size();

        Assert.isTrue(classifierCount>0 , ErrorMessageCode.CLASSIFIER_MATCH_NOT_EXIST.retCheckMessage(aceContext.getAceScene().sceneName));
        Assert.isTrue(classifierCount==1 , ErrorMessageCode.CLASSIFIER_MATCH_BEYOND_ONE.retCheckMessage(aceContext.getAceScene().sceneName));

        Map.Entry<String,AceResult> result = allClassifierResult.entrySet().stream().findFirst().get();
        IClassifier classifier = aceFactory.classifierMap.get(result.getKey());
        return classifier.classify(aceContext);
    }

    private void ValidationClassifier(ImmutableList<String> classifierList) throws Exception{
        for(String classifierName:classifierList){
            if(Objects.isNull(aceFactory.classifierMap.get(classifierName))) {
                throw new Exception(ErrorMessageCode.CLASSIFIER_CHECK_NULL.retCheckMessage(classifierName));
            }
        }
    }

    private AceResult classify(String classifierName , AceContext aceContext) {
        IClassifier classifier = aceFactory.classifierMap.get(classifierName);
        Collection<String> matchers = aceFactory.classifierMatchers.get(classifierName);
        Collection<String> filters = aceFactory.classifierFilters.get(classifierName);
        log.debug("matchers of classifier[{}] is :{}",classifierName,JSON.toJSONString(matchers));
        log.debug("filters of classifier[{}] is :{}",classifierName,JSON.toJSONString(filters));

        List<AceResult> matchersResultList = Lists.newArrayList();
        List<AceResult> filtersResultList = Lists.newArrayList();
        matchers.stream().forEach(ruler -> {
            Method method = aceFactory.rulerMap.get(ruler);
            IAttributor attributor = aceFactory.ruler2AttributorMap.get(ruler);
            String[] rulerParam = aceFactory.classifierRulerParamMap.get(AceUtil.getClassifierRulerName(classifierName,ruler, Constants.MATCHER));
            //获取ruler的参数，并设置到 上下文
            aceContext.setRulerParam(rulerParam);
            log.debug("matcher {} begin to execute ruler",ruler);
            matchersResultList.add((AceResult) ReflectionUtils.invokeMethod(method,attributor,aceContext));
        });

        AceResult matchersAceResult = AceUtil.aggregateResult(matchersResultList);
        log.debug("classifier {} matchers result is :{} , aggregate result:{}",classifierName,JSON.toJSONString(matchersResultList),JSON.toJSONString(matchersAceResult));

        AceResult filtersAceResult = AceResult.success();
        if(!CollectionUtils.isEmpty(filters)) {
            filters.stream().forEach(ruler -> {
                Method method = aceFactory.rulerMap.get(ruler);
                IAttributor attributor = aceFactory.ruler2AttributorMap.get(ruler);
                String[] rulerParam = aceFactory.classifierRulerParamMap.get(AceUtil.getClassifierRulerName(classifierName,ruler, Constants.FILTER));
                //获取ruler的参数，并设置到 上下文
                aceContext.setRulerParam(rulerParam);
                log.debug("filter {} begin to execute ruler",ruler);
                filtersResultList.add((AceResult) ReflectionUtils.invokeMethod(method,attributor,aceContext));
            });
            log.debug("classifier {} filters result is :{}",classifierName,JSON.toJSONString(filtersResultList));
            filtersAceResult = filtersAceResult.and(AceUtil.aggregateResult(AceUtil.negativeAceResultList(filtersResultList)));
        }
        log.debug("filters execute result:{}",filtersAceResult.getIsEffect());
        log.debug("matchers execute result:{}",matchersAceResult.getIsEffect());

        return AceUtil.aggregateResult(matchersAceResult,filtersAceResult);
    }

    private AceResult execute(String executorName , AceContext aceContext){
        IExecutor executor = aceFactory.executorMap.get(executorName);
        Assert.isTrue(!Objects.isNull(executor), aceFactory.executorNullError);

        return executor.execute(aceContext);
    }

    public List<AceResult> execute(AceContext<ImmutableList<String>,Object,Object> aceContext){
        if (Objects.isNull(aceContext)||CollectionUtils.isEmpty(aceContext.getDataParam())) {
            return Lists.newArrayList(AceResult.fail());
        }
        List<AceResult> aceResultList = Lists.newArrayList();
        aceContext.getDataParam().stream().forEach(executorName -> {
            aceResultList.add(execute(executorName,aceContext));
        });
        return aceResultList;
    }

}
