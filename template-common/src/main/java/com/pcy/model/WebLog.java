package com.pcy.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Web的日志记录
 */
@Data
@EqualsAndHashCode
public class WebLog {

    /**
     * 消耗时间，毫秒
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 返回结果
     */
    private Object result;

}
