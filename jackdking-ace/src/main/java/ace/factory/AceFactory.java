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
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AceFactory {

    //校验器集合
    public static Map<String , IAttributor> attributorMap = Maps.newHashMap();
    //分类器集合
    public static Map<String , IClassifier> classifierMap = Maps.newHashMap();
    //执行器集合
    public static Map<String , IExecutor> executorMap = Maps.newHashMap();
    //校验器下的规则器集合
    public static Map<String , Method> rulerMap = Maps.newHashMap();
    //规则器原始规则参数。
    public static Map<String, String[]> rulerParamMap = Maps.newHashMap();

    public static Map<String, IAttributor> ruler2AttributorMap = Maps.newHashMap();
    //规则器定制化规则参数。
    public static Map<String, String[]> classifierRulerParamMap = Maps.newHashMap();

    public static Multimap<String , String> classifierMatchers = ArrayListMultimap.create();

    public static Multimap<String , String> classifierFilters = ArrayListMultimap.create();

    public static Map<String ,String> classifierPriority = Maps.newHashMap();

    private static AceFactory INSTANCE  = new AceFactory();

    public final static Supplier<String> executorNullError = () -> "executor is null";

    private AceFactory(){

    }
    public static AceFactory getInstance() {
        return INSTANCE;
    }
}
