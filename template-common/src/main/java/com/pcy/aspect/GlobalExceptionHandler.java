package com.pcy.aspect;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.pcy.constant.ErrorCodeMsg;
import com.pcy.model.ResponseObject;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1 内部API调用的异常处理
     */
    @ExceptionHandler(value = ApiException.class)
    public ResponseObject handlerApiException(ApiException exception) {
        IErrorCode errorCode = exception.getErrorCode();
        if (errorCode != null) {
            return ResponseObject.failed(ErrorCodeMsg.INTERNAL_SERVER_ERROR_CODE, errorCode.getMsg(), null);
        }
        return ResponseObject.failed(ErrorCodeMsg.INTERNAL_SERVER_ERROR_CODE, exception.getMessage(), null);
    }

    /**
     * 2 方法参数校验失败的异常
     * MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseObject handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                return ResponseObject.failed(ErrorCodeMsg.INTERNAL_SERVER_ERROR_CODE,
                        fieldError.getField() + fieldError.getDefaultMessage(),
                        null);
            }
        }
        return ResponseObject.failed(ErrorCodeMsg.INTERNAL_SERVER_ERROR_CODE, exception.getMessage(), null);
    }

    /**
     * 3 对象内部使用Validate 没有校验成功的异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseObject handlerBindException(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                return ResponseObject.failed(ErrorCodeMsg.INTERNAL_SERVER_ERROR_CODE,
                        fieldError.getField() + fieldError.getDefaultMessage(),
                        null);
            }
        }
        return ResponseObject.failed(ErrorCodeMsg.INTERNAL_SERVER_ERROR_CODE, bindException.getMessage(), null);
    }

}
