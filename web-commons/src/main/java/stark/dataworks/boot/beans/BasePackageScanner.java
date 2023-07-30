package stark.dataworks.boot.beans;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Map;

public class BasePackageScanner
{
    private BasePackageScanner()
    {}

    public static HashSet<String> getBasePackages(ApplicationContext applicationContext)
    {
        HashSet<String> basePackages = new HashSet<>();

        Map<String, Object> applications = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
        for (String applicationName : applications.keySet())
        {
            // Get the class descriptor of the class with the @SpringBootApplication annotation.
            Object application = applications.get(applicationName);
            Class<?> applicationClass = application.getClass();

            // Get the project default base package.
            String packageName = applicationClass.getPackageName();
            basePackages.add(packageName);

            // Get base packages configured in the @SpringBootApplication annotation.
            // Here the "basePackages" of the @ComponentScan annotation is applied by the @SpringBootApplication annotation.
            SpringBootApplication sbaAnnotation = applicationClass.getAnnotation(SpringBootApplication.class);

            // Only packages with text are needed.
            String[] scanBasePackages = sbaAnnotation.scanBasePackages();
            for (String basePackage : scanBasePackages)
            {
                if (StringUtils.hasText(basePackage))
                    basePackages.add(basePackage);
            }

            // There can only be 1 class annotated with the @SpringBootApplication annotation.
            // Thus, the iteration is needed to execute for only once.
            break;
        }

        return basePackages;
    }
}
