package ace.core;

import ace.attributor.IAttributor;
import ace.classifier.IClassifier;
import ace.constant.Constants;
import ace.enums.ErrorMessageCode;
import ace.executor.IExecutor;
import ace.factory.AceFactory;
import ace.utils.AceUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class AceWorker {
    private final static AceWorker INSTANCE = new AceWorker();
    AceFactory aceFactory = AceFactory.getInstance();

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
        log.debug("classifierCount :{}",classifierCount);

        if (!aceResultChecker(classifierCount, aceContext).isPresent()) {
            //业务数据没有对应的分类器，因此也就没有executor流程。在这里就直接return
            return AceResult.empty();
        }

        Map.Entry<String,AceResult> result = allClassifierResult.entrySet().stream().findFirst().get();
        IClassifier classifier = aceFactory.classifierMap.get(result.getKey());
        return classifier.classify(aceContext);
    }

    private AceResult aceResultChecker(long classifierCount, AceContext aceContext) {
        Assert.isTrue(classifierCount <= 1 , ErrorMessageCode.CLASSIFIER_MATCH_BEYOND_ONE.retCheckMessage(aceContext.getAceScene().sceneName));
        if (classifierCount == 0) {
            log.debug("no classifier match, message:{}, aceContext:{}",  ErrorMessageCode.CLASSIFIER_MATCH_NOT_EXIST.retCheckMessage(aceContext.getAceScene().sceneName), JSON.toJSONString(aceContext));
            return AceResult.empty();
        }
        return AceResult.success();
    }

    private void ValidationClassifier(ImmutableList<String> classifierList) throws Exception{
        for(String classifierName:classifierList){
            Optional.ofNullable(aceFactory.classifierMap.get(classifierName))
                    .orElseThrow(() -> new Exception(ErrorMessageCode.CLASSIFIER_CHECK_NULL.retCheckMessage(classifierName)));
        }
    }

    private AceResult classify(String classifierName , AceContext aceContext) {

        Collection<String> matchers = aceFactory.classifierMatchers.get(classifierName);
        log.debug("matchers of classifier[{}] is :{}",classifierName,JSON.toJSONString(matchers));

        Collection<String> filters = aceFactory.classifierFilters.get(classifierName);
        log.debug("filters of classifier[{}] is :{}",classifierName,JSON.toJSONString(filters));

        AceResult matchersAceResult = processMatchers(classifierName, matchers, aceContext);
        log.debug("matchers execute result:{}",matchersAceResult.getIsEffect());

        AceResult filtersAceResult = processFilters(classifierName, filters, aceContext);
        log.debug("filters execute result:{}",filtersAceResult.getIsEffect());

        return AceUtil.aggregateResult(matchersAceResult,filtersAceResult);
    }

    private AceResult processFilters(String classifierName, Collection<String> filters, AceContext aceContext) {
        List<AceResult> filtersResultList = Lists.newArrayList();
        AceResult filtersAceResult = AceResult.success();
        if(!CollectionUtils.isEmpty(filters)) {
            filters.stream().forEach(ruler -> {
                Method method = aceFactory.rulerMap.get(ruler);
                IAttributor attributor = aceFactory.ruler2AttributorMap.get(ruler);
                String[] rulerParam = aceFactory.classifierRulerParamMap.get(AceUtil.getClassifierRulerName(classifierName,ruler, Constants.FILTER));
                //获取ruler的参数，并设置到 上下文
                aceContext.setRulerParam(rulerParam);
                log.debug("filter {} begin to execute ruler",ruler);
                AceResult result = (AceResult) ReflectionUtils.invokeMethod(method,attributor,aceContext);
                filtersResultList.add(result);
            });
            filtersAceResult = filtersAceResult.and(AceUtil.aggregateResult(AceUtil.negativeAceResultList(filtersResultList)));
            log.debug("classifier {} filters result is :{} , aggregate result:{}",classifierName,JSON.toJSONString(filtersResultList),JSON.toJSONString(filtersAceResult));
        }
        return filtersAceResult;
    }

    private AceResult processMatchers(String classifierName, Collection<String> matchers, AceContext aceContext) {
        List<AceResult> matchersResultList = Lists.newArrayList();
        matchers.stream().forEach(ruler -> {
            Method method = aceFactory.rulerMap.get(ruler);
            IAttributor attributor = aceFactory.ruler2AttributorMap.get(ruler);
            String[] rulerParam = aceFactory.classifierRulerParamMap.get(AceUtil.getClassifierRulerName(classifierName,ruler, Constants.MATCHER));
            //获取ruler的参数，并设置到 上下文
            aceContext.setRulerParam(rulerParam);
            log.debug("matcher {} begin to execute ruler",ruler);
            AceResult result = (AceResult) ReflectionUtils.invokeMethod(method,attributor,aceContext);
            matchersResultList.add(result);
        });

        AceResult matchersAceResult = AceUtil.aggregateResult(matchersResultList);
        log.debug("classifier {} matchers result is :{} , aggregate result:{}",classifierName,JSON.toJSONString(matchersResultList),JSON.toJSONString(matchersAceResult));
        return matchersAceResult;
    }

    private AceResult execute(String executorName , AceContext aceContext){
        IExecutor executor = aceFactory.executorMap.get(executorName);
        Assert.isTrue(!Objects.isNull(executor), aceFactory.executorNullError);

        return executor.execute(aceContext);
    }

    public List<AceResult> execute(AceContext<List<String>,Object,Object> aceContext){
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
