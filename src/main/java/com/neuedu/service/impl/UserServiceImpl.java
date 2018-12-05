package com.neuedu.service.impl;

import com.neuedu.common.ServerReponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    /*从容器中获取bean，加注解@Autowired，通过类型去找，前提是实现类也交给容器管理*/
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public ServerReponse register(UserInfo userInfo) {
        int count =  userInfoMapper.insert(userInfo);
        if (count > 0){
            return ServerReponse.createServerResponseBySuccess();
        }
        return ServerReponse.createServerResponseByError();
    }
}
