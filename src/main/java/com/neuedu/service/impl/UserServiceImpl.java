package com.neuedu.service.impl;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerReponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        /*int result = userInfoMapper.checkUsername(username);
        if (result <= 0){//用户名不存在
            return ServerReponse.createServerResponseByError("用户名不存在");
        }*/

        /*调用check_valid()方法*/
        ServerReponse serverReponse = check_valid(username,Const.USERNAME);
        if (serverReponse.isSuccess()){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_EXISTS_USERNAME.getStatus(),ResponseCode.NOT_EXISTS_USERNAME.getMsg());
        }

        //step3:根据用户名和密码查询
        UserInfo userInfo = userInfoMapper.selectUserByUsernameAndPassword(username, MD5Utils.getMD5Code(password));
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
        //step1:进行参数的非空校验
        if (userInfo == null){
            return ServerReponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        //step2:判断用户名是否已经存在
        String username = userInfo.getUsername();
        /*int checkUsername = userInfoMapper.checkUsername(username);
        if (checkUsername > 0){//判断用户名已存在
            return ServerReponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
        }*/
        /*调用check_valid()方法*/
        ServerReponse serverReponse = check_valid(username,Const.USERNAME);
        if (!serverReponse.isSuccess()){
            return ServerReponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
        }

        //step3:判断邮箱是否已经存在
        String email = userInfo.getEmail();
        /*int checkEmail = userInfoMapper.checkEmail(email);
        if (checkEmail < 0){
            return ServerReponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),ResponseCode.EXISTS_EMAIL.getMsg());
        }*/
        /*调用check_valid()方法*/
        ServerReponse serverReponse1 = check_valid(email,Const.EMAIL);
        if (!serverReponse1.isSuccess()){
            return ServerReponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),ResponseCode.EXISTS_EMAIL.getMsg());
        }

        //step4:注册
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        userInfo.setRole(Const.USER_ROLE_CUSTOMER);
        int insert = userInfoMapper.insert(userInfo);
        //step5:返回结果
        if (insert > 0){
            return ServerReponse.createServerResponseBySuccess("注册成功");
        }
        return ServerReponse.createServerResponseByError("注册失败");
    }

    /*检查用户名或密码是否有效*/
    @Override
    public ServerReponse check_valid(String str, String type) {
        //step1:参数的非空校验
        if (StringUtils.isBlank(str) || StringUtils.isBlank(type)){
            return ServerReponse.createServerResponseByError("参数不能为空");
        }
        //step2:判断用户名或者邮箱存在
        if (type.equals(Const.USERNAME)){
            int username_result = userInfoMapper.checkUsername(str);
            if (username_result > 0){
                return ServerReponse.createServerResponseByError(ResponseCode.EXISTS_USERNAME.getStatus(),ResponseCode.EXISTS_USERNAME.getMsg());
            }
            return ServerReponse.createServerResponseBySuccess("用户名不存在");
        }else if (type.equals(Const.EMAIL)){
            int email_result = userInfoMapper.checkEmail(str);
            if (email_result > 0){
                return ServerReponse.createServerResponseByError(ResponseCode.EXISTS_EMAIL.getStatus(),ResponseCode.EXISTS_EMAIL.getMsg());
            }
            return ServerReponse.createServerResponseBySuccess("邮箱不存在");
        }
        //step3:返回结果
        return ServerReponse.createServerResponseBySuccess("type参数传递有误");
    }

    @Override
    public ServerReponse forget_get_question(String username) {
        //step1:参数的非空校验
        if (StringUtils.isBlank(username)){
            return ServerReponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        //step2:判断用户名是否存在
        ServerReponse serverReponse = check_valid(username,Const.USERNAME);
        if (serverReponse.isSuccess()){//用户不存在
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_EXISTS_USERNAME.getStatus(),ResponseCode.NOT_EXISTS_USERNAME.getMsg());
        }
        //step3:通过用户名查询密保问题
        String question = userInfoMapper.getQuestionByUsername(username);
        if (question == null || question.equals("")){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_QUESTION.getStatus(),ResponseCode.NOT_QUESTION.getMsg());
        }
        //step4:返回处理结果
        return ServerReponse.createServerResponseBySuccess(question,null);
    }

    @Override
    public ServerReponse forget_check_answer(String username, String question, String answer) {
        //step1:参数的非空校验
        if (StringUtils.isBlank(username) || StringUtils.isBlank(question) || StringUtils.isBlank(answer)){
            return ServerReponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        //step2:根据username,question,answer查询
        int select = userInfoMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if (select == 0){
            return ServerReponse.createServerResponseByError(ResponseCode.ANSWER_ERROR.getStatus(),ResponseCode.ANSWER_ERROR.getMsg());
        }
        //select!=0，执行下面
        //step3:服务端生成一个token，保存，并将token返回给客户端
        /*生成的forgetToken是唯一的，通过UUID生成，UUID是生成的是一个唯一的，随机生成一个字符串，是唯一的*/
        String forgetToken = UUID.randomUUID().toString();
        /*用到谷歌的一个guava cache（guava缓存）,key也要确保是唯一的，所以用用户名来作为key*/
        TokenCache.set(username,forgetToken);
        return ServerReponse.createServerResponseBySuccess(forgetToken);
    }

    @Override
    public ServerReponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        //step1:参数的非空校验
        if (StringUtils.isBlank(username) || StringUtils.isBlank(passwordNew) || StringUtils.isBlank(forgetToken)){
            return ServerReponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        //step2:校验token
        String token = TokenCache.get(username);
        if (token == null){
            return ServerReponse.createServerResponseByError("token过期失效");
        }
        /*如果两个token是不等的*/
        if (!token.equals(forgetToken)){
            return ServerReponse.createServerResponseByError("无效的token");
        }
        //如果两个token相等，才可以修改密码
        //step3:修改密码
        int update_password = userInfoMapper.updateUserPassword(username,MD5Utils.getMD5Code(passwordNew));
        if (update_password > 0){
            return ServerReponse.createServerResponseBySuccess("修改密码成功");
        }
        return ServerReponse.createServerResponseByError("密码修改失败");
    }
}
