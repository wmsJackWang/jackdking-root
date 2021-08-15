package ace.factory;

import ace.attributor.IAttributor;
import ace.classifier.IClassifier;
import ace.executor.IExecutor;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.*;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AceFactory {

    //校验器集合
    public Map<String , IAttributor> attributorMap = Maps.newHashMap();
    //分类器集合
    public Map<String , IClassifier> classifierMap = Maps.newHashMap();
    //执行器集合
    public Map<String , IExecutor> executorMap = Maps.newHashMap();
    //校验器下的规则器集合
    public Map<String , Method> rulerMap = Maps.newHashMap();
    //规则器原始规则参数。
    public Map<String, String[]> rulerParamMap = Maps.newHashMap();

    public Map<String, IAttributor> ruler2AttributorMap = Maps.newHashMap();
    //规则器定制化规则参数。
    public Map<String, String[]> classifierRulerParamMap = Maps.newHashMap();

    public Multimap<String , String> classifierMatchers = ArrayListMultimap.create();

    public Multimap<String , String> classifierFilters = ArrayListMultimap.create();

    public Map<String ,String> classifierPriority = Maps.newHashMap();

    private static AceFactory INSTANCE  = new AceFactory();

    private AceFactory(){

    }
    public static AceFactory getInstance() {
        return INSTANCE;
    }
}
