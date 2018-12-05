package com.neuedu.service;

import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.UserInfo;

public interface IUserService {

    /**
     * 注册
     * @param userInfo
     * @return
     */
    public ServerReponse register(UserInfo userInfo);

}
