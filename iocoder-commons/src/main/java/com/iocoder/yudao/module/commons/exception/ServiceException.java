package com.iocoder.yudao.module.commons.exception;

/**
 * 业务异常
 *
 * @author kai wu
 */
public final class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ServiceException(String message ,String detailMessage){
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return this.code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}
