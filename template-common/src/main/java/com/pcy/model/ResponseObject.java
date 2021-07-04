package com.pcy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 统一返回结果包装类
 *
 * @author PengChenyu
 * @since 2021-06-27 16:08:26
 */
@ApiModel(value = "ResponseObject", description = "统一出参包装对象")
@Getter
@Setter
public class ResponseObject<T> implements Serializable {

    private static final long serialVersionUID = 353049918578738538L;

    @ApiModelProperty(value = "请求是否成功标志")
    private Boolean flag;

    @ApiModelProperty(value = "错误码")
    private int errCode;

    @ApiModelProperty(value = "错误描述")
    private String errMessage;

    @ApiModelProperty(value = "具体的返回数据")
    private T data;

    private static <T> ResponseObject<T> restResult(Boolean flag, int errCode, String errMessage, T data) {
        ResponseObject<T> response = new ResponseObject<>();
        response.setFlag(flag);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        response.setData(data);
        return response;
    }

    /**
     * 请求成功
     *
     * @param errCode
     * @param errMessage
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseObject<T> success(int errCode, String errMessage, T data) {
        return restResult(Boolean.TRUE, errCode, errMessage, data);
    }

    /**
     * 请求失败
     *
     * @param errCode
     * @param errMessage
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseObject<T> failed(int errCode, String errMessage, T data) {
        return restResult(Boolean.FALSE, errCode, errMessage, data);
    }


}
