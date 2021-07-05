package com.pcy.constant;

/**
 * 错误状态码和描述
 *
 * @author PengChenyu
 * @since 2021-06-27 16:46:25
 */
public class ErrorCodeMsg {
    /**
     * 请求
     */
    public static final int OK_CODE = 200;
    public static final String OK_MESSAGE = "请求成功";
    public static final int BAD_REQUEST_CODE = 400;
    public static final String BAD_REQUEST_MESSAGE = "请求语法错误";
    public static final int UNAUTHORIZED_CODE = 401;
    public static final String UNAUTHORIZED_MESSAGE = "身份未认证";
    public static final int FORBIDDEN_CODE = 403;
    public static final String FORBIDDEN_MESSAGE = "权限错误";
    public static final int NOT_FOUND_CODE = 404;
    public static final String NOT_FOUND_MESSAGE = "资源不存在";
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "服务器内部错误";

    /**
     * 查询
     */
    public static final int QUERY_SUCCESS_CODE = 10000;
    public static final String QUERY_SUCCESS_MESSAGE = "查询成功";
    public static final int QUERY_NULL_CODE = 10001;
    public static final String QUERY_NULL_MESSAGE = "查询结果为空";

    /**
     * 更新
     */
    public static final int UPDATE_SUCCESS_CODE = 20000;
    public static final String UPDATE_SUCCESS_MESSAGE = "修改成功";
    public static final int UPDATE_FAILED_CODE = 20001;
    public static final String UPDATE_FAILED_MESSAGE = "修改失败";

}
