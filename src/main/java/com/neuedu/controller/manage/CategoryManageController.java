package com.neuedu.controller.manage;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.UserInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/category/")
public class CategoryManageController {

    /**
     * 获取品类子节点(平级)
     */
    @RequestMapping(value = "get_category.do")
    public ServerReponse get_category(HttpSession session,Integer categoryId){
        Object object = session.getAttribute(Const.CURRENTUSER);
        if (object != null && object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

}
