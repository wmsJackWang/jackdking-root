package com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.anoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Ruler {

    String name();//规则名称，同一个属性字段会有不同的校验规则，为空、范围值、单个值匹配、多个值匹配等等。

    String[] value() default {};//为空的时候，规则运行参数由分类器传入，不为空时候，分类器传入参数失效。


}
