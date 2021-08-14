package ace.factory;

import ace.attributor.IAttributor;
import ace.classifier.IClassifier;
import ace.executor.IExecutor;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
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
    //规则器规则参数。
    public Map<String , Object> rulerParamMap = Maps.newHashMap();

    private static AceFactory INSTANCE  = new AceFactory();

    private AceFactory(){

    }
    public static AceFactory getInstance() {
        return INSTANCE;
    }
}
