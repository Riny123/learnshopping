package com.neuedu.service.impl;

import com.neuedu.common.ServerReponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    /*从容器中获取bean，加注解@Autowired，通过类型去找，前提是实现类也交给容器管理*/
    @Autowired
    UserInfoMapper userInfoMapper;

    /*登录*/
    @Override
    public ServerReponse login(String username, String password) {
        //step1:进行参数的非空校验
        if (StringUtils.isBlank(username)){
            return ServerReponse.createServerResponseByError("用户名不能为空");
        }
        if (StringUtils.isBlank(password)){
            return ServerReponse.createServerResponseByError("密码不能为空");
        }
        /*StringUtils.isBlank()判断字符串是不是空的，有一种特殊形式是" "直接判断字符串是空*/
        /*StringUtils.isEmpty()判断字符串是不是空的，它不能判断" "是空的*/

        //step2:检查username是否存在
        int result = userInfoMapper.checkUsername(username);
        if (result <= 0){//用户名不存在
            return ServerReponse.createServerResponseByError("用户名不存在");
        }
        //step3:根据用户名和密码查询
        UserInfo userInfo = userInfoMapper.selectUserByUsernameAndPassword(username, password);
        if (userInfo == null){//密码错误
            return ServerReponse.createServerResponseByError("密码错误");
        }
        //step4:处理结果并返回
        /*因为不能将密码返回前端，先将密码置空*/
        userInfo.setPassword("");
        return ServerReponse.createServerResponseBySuccess(userInfo,null);
    }

    /*注册*/
    @Override
    public ServerReponse register(UserInfo userInfo) {
        int count =  userInfoMapper.insert(userInfo);
        if (count > 0){
            return ServerReponse.createServerResponseBySuccess();
        }
        return ServerReponse.createServerResponseByError();
    }
}
