package stark.dataworks.boot.web;

import stark.dataworks.boot.ExceptionLogger;
import stark.dataworks.basic.data.json.JsonSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWriter
{
    private ResponseWriter()
    {
    }

    public static void write(HttpServletResponse response, ServiceResponse<?> serviceResponse)
    {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try
        {
            String responseText = JsonSerializer.serialize(serviceResponse);
            PrintWriter writer = response.getWriter();
            writer.println(responseText);
            response.flushBuffer();
        }
        catch (IOException e)
        {
            ExceptionLogger.logExceptionInfo(e);
        }
    }
}
