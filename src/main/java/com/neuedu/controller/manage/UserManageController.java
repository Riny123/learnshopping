package com.neuedu.controller.manage;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/user/")
public class UserManageController {

    @Autowired
    IUserService iUserService;

    /**
     * 后台管理员登录接口
     */
    @RequestMapping(value = "login.do")
    public ServerReponse login(HttpSession session,String username, String password){
        ServerReponse serverReponse = iUserService.login(username, password);
        if (serverReponse.isSuccess()){
            session.setAttribute(Const.CURRENTUSER,serverReponse.getData());
        }
        return serverReponse;
    }

    /**
     * 用户列表接口
     */
    @RequestMapping(value = "list.do")
    public ServerReponse list(HttpSession session,
                              @RequestParam(value = "role",required = false,defaultValue = "0") Integer role,
                              @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object object = session.getAttribute(Const.CURRENTUSER);
        if (object != null && object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
            //判断用户的权限
            if (userInfo.getRole() != Const.USER_ROLE_ADMIN){
                return ServerReponse.createServerResponseByError(ResponseCode.NOT_AUTHORITY.getStatus(),ResponseCode.NOT_AUTHORITY.getMsg());
            }
            return iUserService.list(role,pageNum, pageSize);
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

}