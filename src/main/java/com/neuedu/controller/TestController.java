package com.neuedu.controller;

import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/*@Controller直接跳转到前端页面*/
/*@RestController往前端返回的是一个json的数据*/
@RestController
@RequestMapping(value = "/portal/user/")
public class TestController {

    @Autowired
    IUserService iUserService;

    @RequestMapping(value = "login.do")
    public ServerReponse login(UserInfo userInfo){
        userInfo.setRole(1);
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        return iUserService.register(userInfo);
    }

}