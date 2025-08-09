package com.permission.auth.controller;

import com.permission.auth.dto.LoginRequest;
import com.permission.auth.dto.LoginResponse;
import com.permission.auth.service.AuthService;
import com.permission.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 认证控制器
 *
 * @author system
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "认证管理", description = "用户登录、登出、刷新令牌等认证相关接口")
@Validated
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户名密码登录，返回JWT令牌")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        log.info("用户登录：{}", request.getUsername());
        
        String clientIp = getClientIp(httpRequest);
        LoginResponse response = authService.login(request, clientIp);
        
        return Result.success(response, "登录成功");
    }
    
    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户登出", description = "用户登出，清除令牌")
    public Result<String> logout(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token != null) {
            authService.logout(token);
        }
        return Result.success("登出成功");
    }
    
    /**
     * 刷新令牌
     */
    @PostMapping("/refresh")
    @Operation(summary = "刷新令牌", description = "使用刷新令牌获取新的访问令牌")
    public Result<LoginResponse> refresh(@RequestParam String refreshToken) {
        LoginResponse response = authService.refreshToken(refreshToken);
        return Result.success(response, "令牌刷新成功");
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/userinfo")
    @Operation(summary = "获取当前用户信息", description = "根据JWT令牌获取当前登录用户的详细信息")
    public Result<LoginResponse.UserInfo> getUserInfo() {
        LoginResponse.UserInfo userInfo = authService.getCurrentUserInfo();
        return Result.success(userInfo);
    }
    
    /**
     * 验证令牌
     */
    @GetMapping("/validate")
    @Operation(summary = "验证令牌", description = "验证JWT令牌是否有效")
    public Result<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);
        return Result.success(isValid);
    }
    
    /**
     * 从请求中获取token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    
    /**
     * 获取客户端IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
    
}