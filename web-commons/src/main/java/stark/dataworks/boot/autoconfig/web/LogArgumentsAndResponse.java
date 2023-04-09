package stark.dataworks.boot.autoconfig.web;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LogArgumentsAndResponse {
}
