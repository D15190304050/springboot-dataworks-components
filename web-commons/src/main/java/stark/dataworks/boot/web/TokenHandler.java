package stark.dataworks.boot.web;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class TokenHandler
{
    private TokenHandler()
    {
    }

    /**
     * Try to get token from HTTP header AUTHORIZATION. If there is no such header, then try to get the token from cookies.
     * @param request
     * @param tokenCookieName
     * @return
     */
    public static String getToken(HttpServletRequest request, String tokenCookieName)
    {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token))
            return token;

        Cookie[] cookies = request.getCookies();
        if (StringUtils.hasText(tokenCookieName) && cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (tokenCookieName.equals(cookie.getName()))
                    token = cookie.getValue();
            }
        }

        return token;
    }
}
