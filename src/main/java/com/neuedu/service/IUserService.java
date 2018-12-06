package com.neuedu.service;

import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.UserInfo;

public interface IUserService {

    /**
     * 登录
     */
    public ServerReponse login(String username,String password);

    /**
     * 注册
     * @param userInfo
     * @return
     */
    public ServerReponse register(UserInfo userInfo);

}
