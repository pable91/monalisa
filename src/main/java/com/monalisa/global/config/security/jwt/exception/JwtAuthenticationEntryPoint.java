package com.monalisa.global.config.security.jwt.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monalisa.global.config.security.jwt.JwtErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Object errorCode  = request.getAttribute("ERROR_CODE");
        if(errorCode.equals(JwtErrorCode.EXPIRED)) {
            setResponse(response);
        }
    }

    private void setResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, String> map = new HashMap<>();
        map.put("status", Integer.toString(HttpServletResponse.SC_UNAUTHORIZED));
        map.put("errorMessage", JwtErrorCode.EXPIRED.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writer().writeValueAsString(map);
        response.getWriter().print(string);
    }
}

