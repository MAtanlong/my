package com.permission.common.security;

import com.permission.common.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWT认证过滤器
 * 从请求头中提取JWT token并验证，设置Spring Security上下文
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        
        try {
            String token = getTokenFromRequest(request);
            
            if (StringUtils.hasText(token) && jwtUtils.validateToken(token)) {
                String username = jwtUtils.getUsernameFromToken(token);
                Long userId = jwtUtils.getUserIdFromToken(token);
                String roles = jwtUtils.getRolesFromToken(token);
                
                // 构建权限列表
                List<SimpleGrantedAuthority> authorities = null;
                if (StringUtils.hasText(roles)) {
                    authorities = Arrays.stream(roles.split(","))
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                            .collect(Collectors.toList());
                }
                
                // 创建认证对象
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                
                // 设置用户详情
                UserPrincipal userPrincipal = new UserPrincipal(userId, username, roles);
                authentication.setDetails(userPrincipal);
                
                // 设置到Spring Security上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                log.debug("用户 {} 认证成功", username);
            }
        } catch (Exception e) {
            log.error("JWT认证失败：{}", e.getMessage());
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }
    
    /**
     * 从请求中获取token
     *
     * @param request HTTP请求
     * @return JWT token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
    
}