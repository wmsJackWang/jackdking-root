package com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.impl;

import com.alibaba.fastjson.JSONObject;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.AceContext;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.AceInitService;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.AceResult;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.AceWorker;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.anoation.Attributor;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.anoation.Ruler;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.attributor.TopicTagAttributor;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.factory.AceFactory;
import com.autonavi.aos.tmp.settle.voucher.gather.common.VirtualOrderCenterInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.unifiedsession.core.json.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AceInitServiceImpl implements AceInitService {

    private AtomicBoolean initialized = new AtomicBoolean(false);
    private AceFactory aceFactory = AceFactory.getInstance();
    private AceWorker aceWorker = AceWorker.getInstance();

    public static void main(String[] args) {
        Attributor attributor = AnnotationUtils.getAnnotation(TopicTagAttributor.class, Attributor.class);
        System.out.println(attributor.name()+":"+attributor.fieldName());
        Object obj = new TopicTagAttributor();

        List<Method> methods = Lists.newArrayList(ReflectionUtils.getDeclaredMethods(TopicTagAttributor.class)).
                stream().filter((m)->{return m.isAnnotationPresent(Ruler.class);}).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(methods)){
            System.out.println("method no exists");
        }
        methods.forEach(method ->System.out.println(method.getName()));
        Map<String,Method> methodMap = Maps.uniqueIndex(methods, new Function<Method, String>() {
            @Override
            public String apply(Method input) {
                return input.getAnnotation(Ruler.class).name();
            }
        });

        JSONObject contextDataParam = new JSONObject();
        contextDataParam.put("topicTags","refund");

        VirtualOrderCenterInfo upperEntity = new VirtualOrderCenterInfo();
        upperEntity.setTags("refund");
        contextDataParam.put("upperEntity",upperEntity);

        AceContext aceContext = AceContext.of(contextDataParam,"checkTopicTag");

        Method ruler1 = methodMap.get("checkTopicTag");
        Object result = ReflectionUtils.invokeMethod(ruler1,obj,aceContext);
        AceResult aceResult = (AceResult) result;
        System.out.println("校验器检车结果："+aceResult.getIsEffect());

    }


    @Override
    public void init() {
        if(initialized.get()) {
            log.info("Ace already init ");
            return;
        }
    }

    @Override
    public void parseAnnoation() {

    }

    @Override
    public void parseAttributor() {

    }

    @Override
    public void parseClassifier() {

    }

    @Override
    public void parseExecutor() {

    }

    @Override
    public void complete() {

    }
}
