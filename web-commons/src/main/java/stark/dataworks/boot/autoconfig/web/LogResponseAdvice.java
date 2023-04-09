package stark.dataworks.boot.autoconfig.web;

import stark.dataworks.basic.ExceptionInfoFormatter;
import stark.dataworks.basic.data.json.JsonSerializer;
import stark.dataworks.boot.web.CommonErrorResponses;
import stark.dataworks.boot.web.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogResponseAdvice
{

    public LogResponseAdvice()
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

    @Around("(returnTypePointcut() && classAnnotationPointcut()) || methodAnnotationPointcut()")
    public Object logArgumentsAndResult(ProceedingJoinPoint proceedingJoinPoint)
    {
        this.logArguments(proceedingJoinPoint);
        return this.handleAfter(proceedingJoinPoint);
    }

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

    private Object handleAfter(ProceedingJoinPoint proceedingJoinPoint)
    {
        try
        {
            Object result = proceedingJoinPoint.proceed();
            log.info("Result of " + ArgumentBase.getMethodPath(proceedingJoinPoint) + " = " + JsonSerializer.serialize(result));
            return result;
        }
        catch (Throwable e)
        {
            String exceptionInfo = ExceptionInfoFormatter.formatMessageAndStackTrace(e);
            String errorMessage = "Error when executing " + ArgumentBase.getMethodPath(proceedingJoinPoint) + "... See log for more information...";
            log.error(errorMessage + ": " + exceptionInfo);
            return ServiceResponse.buildErrorResponse(CommonErrorResponses.SYSTEM_ERROR.getCode(), errorMessage);
        }
    }
}
