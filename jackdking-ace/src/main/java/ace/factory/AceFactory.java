package ace.factory;

import ace.attributor.IAttributor;
import ace.classifier.IClassifier;
import ace.executor.IExecutor;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

public class AceFactory {

    public Map<String , IAttributor> attributorMap = Maps.newHashMap();
    public Map<String , IClassifier> classifierMap = Maps.newHashMap();
    public Map<String , IExecutor> executorMap = Maps.newHashMap();
    public Map<String , Method> rulerMap = Maps.newHashMap();
    public Map<String , Object> rulerParamMap = Maps.newHashMap();

    private static AceFactory INSTANCE  = new AceFactory();

    private AceFactory(){

    }
    public static AceFactory getInstance() {
        return INSTANCE;
    }
}
