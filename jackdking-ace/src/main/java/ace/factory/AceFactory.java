package com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.factory;

import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.attributor.IAttributor;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.classifier.IClassifier;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.executor.IExecutor;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

public class AceFactory {

    public Map<String , IAttributor> attributorMap = Maps.newHashMap();
    public Map<String , IClassifier> classifierMap = Maps.newHashMap();
    public Map<String , IExecutor> executorMap = Maps.newHashMap();
    public Map<String , Method> rulerMap = Maps.newHashMap();
    public Map<String , Object> rulerParamMap = Maps.newHashMap();

    private static AceFactory aceFactory ;

    private AceFactory(){

    }
    public static AceFactory getInstance() {
        if(aceFactory==null) {
            synchronized (AceFactory.class) {
                if(aceFactory==null) {
                    aceFactory = new AceFactory();
                }
            }
        }
        return aceFactory;
    }
}
