package stark.dataworks.boot.web;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class TokenHandler
{
    private TokenHandler()
    {
    }

    public static String getToken(HttpServletRequest request, String tokenCookieName)
    {
        // Try to get token from HTTP header AUTHORIZATION.
        // If there is no such header, then try to get the token from cookies.

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(token) && StringUtils.hasText(tokenCookieName))
        {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies)
            {
                if (tokenCookieName.equals(cookie.getName()))
                    token = cookie.getValue();
            }
        }

        return token;
    }
}
