package com.neuedu.common;

public class Const {

    //ServerResponse的成功与失败
    public static final Integer SUCCESS_CODE = 0;
    public static final Integer ERROR_CODE = 1;

    //用户角色的定义
    public static final Integer USER_ROLE_CUSTOMER = 0;
    public static final Integer USER_ROLE_ADMIN = 1;
    public static final Integer USER_ROLE_TOP_ADMIN = 2;

    //当前获取的信息
    public static final String CURRENTUSER = "CURRENTUSER";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    //对商品状态的定义
    public static final Integer PRODUCT_STATUS_UP = 1;
    public static final Integer PRODUCT_STATUS_DOWN = 2;
    public static final Integer PRODUCT_STATUS_DELETE = 3;

}
