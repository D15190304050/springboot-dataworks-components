package stark.dataworks.boot.autoconfig.dubbo;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stark.dataworks.boot.dubbo.ValidateArgs;
import stark.dataworks.boot.web.ServiceResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Dubbo 服务参数校验过滤器
 */
@Activate(group = {CommonConstants.PROVIDER}, order = -1000) // 在服务提供者端激活，order 确保提前执行
@Component
public class ValidationFilter implements Filter
{

    // 注入 JSR-303/Hibernate Validator 的校验器
    @Autowired
    private Validator validator;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException
    {
        // 1. 检查方法或接口/类上是否有 @ValidateArgs 注解
        Method method = getMethod(invoker, invocation);

        // 核心修改：检查方法或所属接口上是否存在注解
        boolean shouldValidate = false;
        if (method != null)
        {
            // 检查方法上是否有注解
            shouldValidate = method.isAnnotationPresent(ValidateArgs.class);

            // 如果方法上没有，则检查接口/类上是否有注解
            if (!shouldValidate)
            {
                shouldValidate = method.getDeclaringClass().isAnnotationPresent(ValidateArgs.class);
            }
        }

        if (shouldValidate)
        {
            // 2. 获取方法参数
            Object[] arguments = invocation.getArguments();

            // 3. 遍历参数并执行校验
            for (Object arg : arguments)
            {
                if (arg != null)
                {
                    Set<ConstraintViolation<Object>> violations = validator.validate(arg);

                    if (!violations.isEmpty())
                    {
                        // 校验不通过，构建统一的错误响应并直接返回
                        String errorMessage = violations.iterator().next().getMessage(); // 仅取第一个错误信息

                        // 注意：这里假设你的 ServiceResponse 结构是统一的
                        ServiceResponse<Object> errorResponse = ServiceResponse.buildErrorResponse(-101, "参数校验失败: " + errorMessage);

                        // 使用 AsyncRpcResult 包装结果并返回，阻止方法继续执行
                        return AsyncRpcResult.newDefaultAsyncResult(errorResponse, invocation);
                    }
                }
            }
        }

        // 4. 校验通过或无需校验，继续执行后续 Filter 和业务方法
        return invoker.invoke(invocation);
    }

    // 辅助方法：通过反射获取实际调用的 Method 对象（保持不变，它返回的是接口上的方法）
    private Method getMethod(Invoker<?> invoker, Invocation invocation)
    {
        try
        {
            Class<?> serviceType = invoker.getInterface();
            return serviceType.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        }
        catch (NoSuchMethodException e)
        {
            return null; // 接口中找不到该方法，不应该发生
        }
    }
}