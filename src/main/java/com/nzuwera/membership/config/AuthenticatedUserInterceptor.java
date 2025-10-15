package com.nzuwera.membership.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserInterceptor implements HandlerInterceptor {

    private static final String DEFAULT_SUCCESS_URL = "/home";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if user is authenticated (not null, authenticated, and not anonymous)
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && "anonymousUser".equals(authentication.getPrincipal()))) {

            String requestUri = request.getRequestURI();

            // Redirect authenticated users away from public auth pages
            if (requestUri.equals("/") || requestUri.equals("/login") || requestUri.equals("/register")) {
                response.sendRedirect(DEFAULT_SUCCESS_URL);
                return false;
            }
        }

        return true;
    }
}