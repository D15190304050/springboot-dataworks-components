package stark.dataworks.boot.autoconfig.web;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@NoArgsConstructor
public class LogRedisKeysAdvice
{
    @Pointcut("execution(public java.lang.String *.*(..))")
    public void returnTypePointcut()
    {
    }

    @Pointcut("@within(stark.dataworks.boot.autoconfig.web.LogRedisKeys)")
    public void classAnnotationPointcut()
    {
    }

    @AfterReturning(pointcut = "returnTypePointcut() && classAnnotationPointcut()", returning = "result")
    public void logKey(Object result)
    {
        log.info("The key for the redis operation is: " + result);
    }
}
