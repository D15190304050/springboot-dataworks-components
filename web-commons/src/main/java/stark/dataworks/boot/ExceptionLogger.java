package stark.dataworks.boot;

import lombok.extern.slf4j.Slf4j;
import stark.dataworks.basic.ExceptionInfoFormatter;

@Slf4j
public class ExceptionLogger
{
    private ExceptionLogger()
    {
    }

    public static void logExceptionInfo(Throwable e)
    {
        String exceptionInfo = ExceptionInfoFormatter.formatMessageAndStackTrace(e);
        log.error(exceptionInfo);
    }
}
