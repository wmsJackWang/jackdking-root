package ace.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Classifier {
    String name();
    //matcher ，filter 与 priority不能同时存在
    Ruler[] matcher();
    Ruler[] filter() ;
    String priority() default "";//宏分类器吗，包含多个原子分类器,表明了分类器的优先级
}