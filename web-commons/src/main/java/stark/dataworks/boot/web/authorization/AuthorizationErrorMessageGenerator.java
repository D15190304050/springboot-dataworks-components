package stark.dataworks.boot.web.authorization;

import org.springframework.util.StringUtils;

import java.util.List;

public class AuthorizationErrorMessageGenerator
{
    public static String resourceNotPermitted(long userId, List<Long> resourceIds, String resourceTypeName)
    {
        String idsString = StringUtils.collectionToCommaDelimitedString(resourceIds);
        return String.format("User %d does not have permission of %s with ID in (%s)", userId, resourceTypeName, idsString);
    }
}
