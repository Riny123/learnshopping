package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerReponse;
import com.neuedu.dao.UserInfoMapper;
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

}