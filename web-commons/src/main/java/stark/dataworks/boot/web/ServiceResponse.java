package stark.dataworks.boot.web;

import stark.dataworks.basic.data.json.JsonSerializer;

import java.util.HashMap;

public class ServiceResponse<TData>
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

    public static<TData> ServiceResponse<TData> buildErrorResponse(CommonErrorResponses response)
    {
        return new ServiceResponse<>(response.getCode(), false, null, response.getMessage(), null);
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public TData getData()
    {
        return data;
    }

    public void setData(TData data)
    {
        this.data = data;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public HashMap<String, Object> getOther()
    {
        return other;
    }

    public void setOther(HashMap<String, Object> other)
    {
        this.other = other;
    }

    @Override
    public String toString()
    {
        return JsonSerializer.serialize(this);
    }
}
