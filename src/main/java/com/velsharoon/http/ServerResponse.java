package com.velsharoon.http;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerResponse<T> {

    private int status;
    private T data;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    public ServerResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ServerResponse(int status) {
        this.status = status;
    }

    public ServerResponse(int statu, String message) {
        this.status = statu;
        this.message = message;
    }

    //使之不在序列化结果中
    @JsonIgnore
    public boolean checkIsSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    /**
     * 成功返回数据
     *
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data, msg);
    }

    /**
     * 用于校验登录信息
     *
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createByCheckSuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 返回错误信息
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ServerResponse<T> createByError(String msg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> ServerResponse<T> createByNeedLogin() {
        return new ServerResponse<T>(ResponseCode.NEED_LOG.getCode(), "请先登录！");
    }
}