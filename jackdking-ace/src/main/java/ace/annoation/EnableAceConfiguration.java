package ace.annoation;


import ace.conf.AceConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AceConfiguration.class)
@Documented
public @interface EnableAceConfiguration {
}
