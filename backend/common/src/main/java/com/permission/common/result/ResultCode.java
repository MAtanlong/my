package com.permission.common.result;

/**
 * 枚举了一些常用API操作码
 *
 * @author system
 * @since 2024-01-01
 */
public enum ResultCode {
    
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    NOT_FOUND(404, "资源未找到"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    
    // 业务相关错误码
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXISTS(1002, "用户已存在"),
    USER_DISABLED(1003, "用户已被禁用"),
    PASSWORD_ERROR(1004, "密码错误"),
    OLD_PASSWORD_ERROR(1005, "原密码错误"),
    
    ROLE_NOT_FOUND(2001, "角色不存在"),
    ROLE_ALREADY_EXISTS(2002, "角色已存在"),
    ROLE_CODE_EXISTS(2003, "角色编码已存在"),
    ROLE_HAS_USERS(2004, "角色下存在用户，无法删除"),
    
    PERMISSION_NOT_FOUND(3001, "权限不存在"),
    PERMISSION_ALREADY_EXISTS(3002, "权限已存在"),
    PERMISSION_DENIED(3003, "权限不足"),
    PERMISSION_CODE_EXISTS(3004, "权限编码已存在"),
    PERMISSION_PARENT_NOT_FOUND(3005, "父权限不存在"),
    PERMISSION_HAS_CHILDREN(3006, "权限下存在子权限，无法删除"),
    PERMISSION_HAS_ROLES(3007, "权限已分配给角色，无法删除"),
    
    MENU_NOT_FOUND(4001, "菜单不存在"),
    MENU_ALREADY_EXISTS(4002, "菜单已存在"),
    MENU_HAS_CHILDREN(4003, "菜单下存在子菜单，无法删除"),
    MENU_CODE_EXISTS(4004, "菜单编码已存在"),
    MENU_PARENT_NOT_FOUND(4005, "父菜单不存在"),
    
    DEPT_NOT_FOUND(5001, "部门不存在"),
    DEPT_ALREADY_EXISTS(5002, "部门已存在"),
    DEPT_HAS_CHILDREN(5003, "部门下存在子部门，无法删除"),
    DEPT_HAS_USERS(5004, "部门下存在用户，无法删除"),
    
    CONFIG_NOT_FOUND(6001, "配置不存在"),
    CONFIG_KEY_EXISTS(6002, "配置键名已存在"),
    
    DICT_TYPE_NOT_FOUND(7001, "字典类型不存在"),
    DICT_TYPE_EXISTS(7002, "字典类型已存在"),
    DICT_DATA_NOT_FOUND(7003, "字典数据不存在"),
    DICT_DATA_EXISTS(7004, "字典数据已存在"),
    DICT_DATA_VALUE_EXISTS(7005, "字典值已存在");
    
    private final Integer code;
    private final String message;
    
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
}