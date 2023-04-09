package stark.dataworks.boot.autoconfig.web;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public class ArgumentBase {
    private ArgumentBase() {
    }

    public static Class<?>[] getParameterTypes(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        return methodSignature.getParameterTypes();
    }

    public static String getMethodPath(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringTypeName() + "#" + signature.getName();
    }
}
