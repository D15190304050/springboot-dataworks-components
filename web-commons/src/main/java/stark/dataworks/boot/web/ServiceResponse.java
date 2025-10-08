package stark.dataworks.boot.web;

import lombok.Data;
import org.springframework.http.MediaType;
import stark.dataworks.basic.data.json.JsonSerializer;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

@Data
public class ServiceResponse<TData> implements Serializable
{
    public static final int SUCCESS_CODE = 0;

    private int code;
    private boolean success;
    private TData data;
    private String message;
    private HashMap<String, Object> other;

    public ServiceResponse()
    {
    }

    public ServiceResponse(int code, boolean success, TData data, String message, HashMap<String, Object> other)
    {
        this.code = code;
        this.success = success;
        this.data = data;
        this.message = message;
        this.other = other;
    }

    public static<TData> ServiceResponse<TData> buildSuccessResponse(TData data)
    {
        return new ServiceResponse<>(0, true, data, "", null);
    }

    public static<TData> ServiceResponse<TData> buildSuccessResponse(TData data, String message)
    {
        return new ServiceResponse<>(0, true, data, message, null);
    }

    public static<TData> ServiceResponse<TData> buildErrorResponse(int code, String message)
    {
        return new ServiceResponse<>(code, false, null, message, null);
    }

    public Object getExtra(String key)
    {
        if (other == null)
            return null;

        return other.get(key);
    }

    public void putExtra(String key, Object value)
    {
        if (other == null)
            other = new HashMap<>();

        other.put(key, value);
    }

    @Override
    public String toString()
    {
        return JsonSerializer.serialize(this);
    }

    public void writeToResponse(HttpServletResponse response) throws IOException
    {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String resultJson = JsonSerializer.serialize(this);
        response.getWriter().println(resultJson);
        response.flushBuffer();
    }
}
