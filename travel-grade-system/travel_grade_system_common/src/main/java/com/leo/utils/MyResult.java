package com.leo.utils;

import lombok.Data;

/*
 *              200：表示成功
 * 				500：表示错误，错误信息在msg字段中
 * 				501：bean验证错误，不管多少个错误都以map形式返回
 * 				502：拦截器拦截到用户token出错
 * 				555：异常抛出信息
 */
@Data
public class MyResult {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static MyResult build(Integer status, String msg, Object data) {
        return new MyResult(status, msg, data);
    }

    public static MyResult ok(Object data) {
        return new MyResult(data);
    }

    public static MyResult ok() {
        return new MyResult(null);
    }

    public static MyResult errorMsg(String msg) {
        return new MyResult(500, msg, null);
    }

    public static MyResult errorMap(Object data) {
        return new MyResult(501, "error", data);
    }

    public static MyResult errorTokenMsg(String msg) {
        return new MyResult(502, msg, null);
    }

    public static MyResult errorException(String msg) {
        return new MyResult(555, msg, null);
    }

    public MyResult() {

    }

    public MyResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public MyResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == 200;
    }
}
