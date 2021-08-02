package annotation.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface RequestMapping {

    String name() default "";

    @AliasFor("path")
    String[] value() default {};


    @AliasFor("value")
    String[] path() default {};
}