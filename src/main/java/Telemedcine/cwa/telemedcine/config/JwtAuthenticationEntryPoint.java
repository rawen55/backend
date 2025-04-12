package Telemedcine.cwa.telemedcine.config;
import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // This method is invoked when an unauthenticated user attempts to access a protected resource
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        // Send a 401 Unauthorized status code
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Authentication token is missing or invalid");
    }
    
}
