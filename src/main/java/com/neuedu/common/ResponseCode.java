package com.neuedu.common;

/**
 * 定义响应状态码
 */
public enum ResponseCode {

    NOT_PARAM_EMPTY(2,"参数不能为空"),
    EXISTS_USERNAME(3,"用户名已存在"),
    EXISTS_EMAIL(4,"邮箱已存在"),
    NOT_EXISTS_USERNAME(5,"用户名不存在"),
    USER_NOT_LOGIN(6,"用户名未登录"),
    NOT_QUESTION(7,"未设置找回密码问题"),
    ANSWER_ERROR(8,"问题答案错误"),
    NOT_AUTHORITY(9,"无权限操作")
    ;
    private int status;
    private String msg;

    ResponseCode() {
    }

    ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
