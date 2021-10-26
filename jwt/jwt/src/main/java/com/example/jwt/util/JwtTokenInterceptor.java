package com.example.jwt.util;

import com.example.jwt.domain.Auth;
import com.example.jwt.service.AuthService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public JwtTokenInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String authorization = request.getHeader("Authorization");
        String[] bearer_s = authorization.split("Bearer ");
        String accessToken = bearer_s[1];

        if (accessToken != null) {
            if (authService.isValidToken(accessToken)) {
                return true;
            } else {
                String uid = authService.getExpiredSubject(accessToken);
                Auth auth = authService.findAuthByAccessToken(accessToken);

                if (auth == null) {
                    response.setContentType("application/json");
                    response.getWriter().println("{\"success\":false,\"status\":401,\"data\":\"Unauthorized Token\"}");
                    return false;
                }

                if (!authService.isValidToken(auth.getRefreshToken())) {
                    response.setContentType("application/json");
                    response.getWriter().println("{\"success\":false,\"status\":401,\"data\":\"Unauthorized Token\"}");
                    return false;
                }

                if (uid.equals(authService.getSubject(authService.getClaimsByToken(auth.getRefreshToken())))) {
                    String newAccessToken = authService.createToken(uid, 1 * 60 * 1000L);
                    String newRefreshToken = authService.createToken(uid, 30 * 24 * 60 * 60 * 1000L);
                    authService.deleteAuthByEmail(auth.getEmail());
                    Auth newAuth = authService.createAuth(newAccessToken,newRefreshToken, auth.getEmail());
                    authService.saveAuth(newAuth);

                    request.setAttribute("uid", auth.getEmail());
                    response.setHeader("Authorization", "Bearer "+newAccessToken);
                }
                return true;
            }
        } else {
            response.setContentType("application/json");
            response.getWriter().println("{\"success\":false,\"status\":401,\"data\":\"Unauthorized Token\"}");
            return false;
        }
    }
}
