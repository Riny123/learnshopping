package com.neuedu.service;

import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.UserInfo;

public interface IUserService {

    /**
     * 登录
     */
    ServerReponse login(String username,String password);

    /**
     * 注册
     * @param userInfo
     * @return
     */
    ServerReponse register(UserInfo userInfo);

    /**
     * 检查用户名或邮箱是否有效
     */
    ServerReponse check_valid(String str,String type);

    /**
     * 根据用户名查询密保问题
     */
    ServerReponse forget_get_question(String username);

    /**
     * 提交问题答案
     */
    ServerReponse forget_check_answer(String username,String question,String answer);

    /**
     * 忘记密码的重设密码
     */
    ServerReponse forget_reset_password(String username,String passwordNew,String forgetToken);

    /**
     * 登录状态下重置密码
     */
    ServerReponse reset_password(String username,String passwordOld,String passwordNew);

    /**
     * 登录状态下修改个人信息
     */
    ServerReponse update_information(UserInfo user);

    /**
     * 后台管理员登录
     */


}
