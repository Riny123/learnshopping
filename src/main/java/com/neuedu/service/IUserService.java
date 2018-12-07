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

    /**
     * 检查用户名或邮箱是否有效
     */
    public ServerReponse check_valid(String str,String type);

    /**
     * 根据用户名查询密保问题
     */
    public ServerReponse forget_get_question(String username);

    /**
     * 提交问题答案
     */
    public ServerReponse forget_check_answer(String username,String question,String answer);

    /**
     * 忘记密码的重设密码
     */
    public ServerReponse forget_reset_password(String username,String passwordNew,String forgetToken);

}
