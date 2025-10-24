package stark.dataworks.boot.web;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
    public static String getTokenFromRequest(HttpServletRequest request, String tokenCookieName)
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

    private static String getTokenFromCookies(ServerHttpRequest request, String tokenCookieName)
    {
        if (!StringUtils.hasText(tokenCookieName))
            return null;

        HttpCookie cookie = request.getCookies().getFirst(tokenCookieName);
        return cookie != null ? cookie.getValue() : null;
    }

    private static String getTokenFromHeaders(ServerHttpRequest request)
    {
        return request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    }

    /**
     * Get token from request. Here the token in the header is preferred over that in the cookie.
     * @param request
     * @return
     */
    public static String getTokenFromRequest(ServerHttpRequest request, String tokenCookieName)
    {
        String tokenFromHeaders = getTokenFromHeaders(request);
        return tokenFromHeaders != null ? tokenFromHeaders : getTokenFromCookies(request, tokenCookieName);
    }
}
