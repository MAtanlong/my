package com.permission.common.exception;

import com.permission.common.result.ResultCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author system
 * @since 2024-01-01
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 错误码
     */
    private final ResultCode resultCode;
    
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }
    
    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
    
    public BusinessException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILED;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCode.FAILED;
    }
    
}