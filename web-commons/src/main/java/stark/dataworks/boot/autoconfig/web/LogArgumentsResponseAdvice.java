package stark.dataworks.boot.autoconfig.web;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import stark.dataworks.basic.data.json.JsonSerializer;

@Slf4j
@Aspect
@Component
public class LogArgumentsResponseAdvice
{

    public LogArgumentsResponseAdvice()
    {
    }

    @Pointcut("execution(public stark.dataworks.boot.web.ServiceResponse *.*(..))")
    public void returnTypePointcut()
    {
    }

    @Pointcut("@within(stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse)")
    public void classAnnotationPointcut()
    {
    }

    @Pointcut("@annotation(stark.dataworks.boot.autoconfig.web.LogArgumentsAndResponse)")
    public void methodAnnotationPointcut()
    {
    }

    @Pointcut("(returnTypePointcut() && classAnnotationPointcut()) || methodAnnotationPointcut()")
    public void advicePointcut()
    {
    }


    @Before("advicePointcut()")
    private void logArguments(JoinPoint joinPoint)
    {
        Object[] arguments = joinPoint.getArgs();
        if (arguments.length > 0)
        {
            Class<?>[] parameterTypes = ArgumentBase.getParameterTypes(joinPoint);
            log.info("Log arguments of " + ArgumentBase.getMethodPath(joinPoint));

            for (int i = 0; i < arguments.length; ++i)
            {
                log.info("Argument [" + i + "] (" + parameterTypes[i].getName() + ") = " + JsonSerializer.serialize(arguments[i]));
            }
        }
    }

    @AfterReturning(value = "advicePointcut()", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response)
    {
        log.info("Result of " + ArgumentBase.getMethodPath(joinPoint) + " = " + JsonSerializer.serialize(response));
    }
}
