package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/portal/user/")
public class UserController {

    @Autowired
    IUserService iUserService;

    /**
     * 登录
     * @param session
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "login.do")
    public ServerReponse login(HttpSession session, String username, String password){
        ServerReponse serverReponse = iUserService.login(username, password);
        //判断是不是成功的访问接口
        if (serverReponse.isSuccess()){
            //登录成功之后要把登录信息保存在session中
            session.setAttribute(Const.CURRENTUSER,serverReponse.getData());
        }
        return serverReponse;
    }

    /**
     * 注册
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "register.do")
    public ServerReponse register(UserInfo userInfo){
        return iUserService.register(userInfo);
    }

    /**
     * 检查用户名是否有效
     */
    @RequestMapping(value = "check_valid.do")
    public ServerReponse check_valid(String str,String type){
        return iUserService.check_valid(str, type);
    }

    /**
     * 获取当前登录用户信息
     */
    @RequestMapping(value = "get_user_info.do")
    public ServerReponse get_user_info(HttpSession session){
        //获取用户登录成功之后的session值
        Object object = session.getAttribute(Const.CURRENTUSER);
        //判断object存在并且object属于UserInfo类型
        if (object != null && object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
            UserInfo userInfo1 = new UserInfo();
            userInfo1.setId(userInfo.getId());
            userInfo1.setUsername(userInfo.getUsername());
            userInfo1.setEmail(userInfo.getEmail());
            userInfo1.setPhone(userInfo.getPhone());
            userInfo1.setCreateTime(userInfo.getCreateTime());
            userInfo1.setUpdateTime(userInfo.getUpdateTime());
            return ServerReponse.createServerResponseBySuccess(userInfo1,null);

        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 获取当前登录用户的详细信息
     */
    @RequestMapping(value = "get_inforamtion.do")
    public ServerReponse get_inforamtion(HttpSession session){
        Object o = session.getAttribute(Const.CURRENTUSER);
        if (o != null && o instanceof UserInfo){
            UserInfo userInfo = (UserInfo) o;
            return ServerReponse.createServerResponseBySuccess(userInfo,null);
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 根据用户名查询密保问题
     */
    @RequestMapping(value = "forget_get_question")
    public ServerReponse forget_get_question(String username){
        return iUserService.forget_get_question(username);
    }

    /**
     * 提交问题答案接口
     */
    @RequestMapping(value = "forget_check_answer.do")
    public ServerReponse forget_check_answer(String username,String question,String answer){
        return iUserService.forget_check_answer(username, question, answer);
    }

    /**
     * 忘记密码的重设密码
     */
    @RequestMapping(value = "forget_reset_password.do")
    public ServerReponse forget_reset_password(String username,String passwordNew,String forgetToken){
        return iUserService.forget_reset_password(username, passwordNew, forgetToken);
    }

    /**
     * 登录状态下重置密码
     */
    @RequestMapping(value = "reset_password.do")
    public ServerReponse reset_password(HttpSession session,String passwordOld,String passwordNew){
        Object object = session.getAttribute(Const.CURRENTUSER);
        if (object != null && object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
            return iUserService.reset_password(userInfo.getUsername(),passwordOld,passwordNew);
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 登录状态下更新个人信息
     */
    @RequestMapping(value = "update_information.do")
    public ServerReponse update_information(HttpSession session,UserInfo user){
        Object object = session.getAttribute(Const.CURRENTUSER);
        if (object != null && object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
            user.setId(userInfo.getId());
            return iUserService.update_information(user);
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "logout.do")
    public ServerReponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENTUSER);
        return ServerReponse.createServerResponseBySuccess("已退出登录");
    }

}