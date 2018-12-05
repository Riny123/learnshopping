package com.neuedu.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 封装返回前端的高复用对象
 * @param <T>
 */
/*当对ServerReaponse这个对象转成json字符串的时候，空的字符串就可以不显示*/
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerReponse<T> {

    /*状态码*/
    private int status;
    /*传入的数据不知道是什么类型，用泛型*/
    private T data;
    /*接口的提示信息*/
    private String msg;

    /*提供构造方法：私有的，不能被其他的类创建*/
    private ServerReponse(){}
    private ServerReponse(int status){
        this.status = status;
    }
    private ServerReponse(int status,T data){
        this.status = status;
        this.data = data;
    }
    private ServerReponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
    private ServerReponse(int status,T data,String msg){
        this.status = status;
        this.data = data;
        this.msg = msg;
    }



    /*判断接口是否调用成功*/
    /*在转json的时候这个字段自动忽略掉*/
    @JsonIgnore
    public boolean isSuccess(){
        return  this.status == Const.SUCCESS_CODE;
    }



    /*因为构造方法是私有的，所以需要对外提供一个创建ServerReponse的方法*/
    /**
     * 当接口返回成功时：四种情况
     *                   1.仅仅返回status
     *                   2.返回status和msg
     *                   3.返回status和data
     *                   4.返回status和msg和data
     * @return
     */
    /*仅仅返回status*/
    public static ServerReponse createServerResponseBySuccess(){
        return new ServerReponse(Const.SUCCESS_CODE);
    }
    /*返回status和data 引用泛型参数，把其方法也改成泛型方法*/
    public static <T> ServerReponse createServerResponseBySuccess(T data){
        return new ServerReponse(Const.SUCCESS_CODE,data);
    }
    /*返回status和msg*/
    public static ServerReponse createServerResponseBySuccess(String msg){
        return new ServerReponse(Const.SUCCESS_CODE,msg);
    }
    /*返回status和msg和data*/
    public static <T> ServerReponse createServerResponseBySuccess(T data,String msg){
        return new ServerReponse(Const.SUCCESS_CODE,data,msg);
    }

    /**
     * 当接口返回失败时：两种情况
     *                   1.仅仅返回status
     *                   2.返回status和msg
     *                   3.自定义错误状态码
     * @return
     */
    /*仅仅返回status*/
    public static ServerReponse createServerResponseByError(){
        return new ServerReponse(Const.ERROR_CODE);
    }
    /*返回status和msg*/
    public static ServerReponse createServerResponseByError(String msg){
        return new ServerReponse(Const.ERROR_CODE,msg);
    }
    /*自定义错误状态码*/
    public static ServerReponse createServerResponseByError(int status,String msg){
        return new ServerReponse(status,msg);
    }



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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}