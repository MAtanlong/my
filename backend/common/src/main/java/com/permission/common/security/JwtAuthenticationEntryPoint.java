package com.permission.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.permission.common.result.Result;
import com.permission.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证入口点
 * 当用户尝试访问受保护的资源但未提供有效的JWT token时，会调用此类
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        
        log.warn("未授权访问：{} - {}", request.getRequestURI(), authException.getMessage());
        
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        Result<String> result = Result.failed(ResultCode.UNAUTHORIZED, "访问被拒绝，请先登录");
        
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(result);
        
        response.getWriter().write(jsonResult);
        response.getWriter().flush();