package com.neuedu.controller.manage;

import com.neuedu.common.Const;
import com.neuedu.common.ServerReponse;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

}