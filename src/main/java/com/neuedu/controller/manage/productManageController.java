package com.neuedu.controller.manage;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/product/")
public class productManageController {

    @Autowired
    IProductService iProductService;

    /**
     * 新增商品或更新商品
     */
    @RequestMapping(value = "save.do")
    public ServerReponse addOrUpdate(HttpSession session,Product product){
       Object object = session.getAttribute(Const.CURRENTUSER);
       if (object != null &&  object instanceof UserInfo){
           UserInfo userInfo = (UserInfo) object;
           //判断用户权限
           if (userInfo.getRole() != Const.USER_ROLE_ADMIN){
               return ServerReponse.createServerResponseByError(ResponseCode.NOT_AUTHORITY.getStatus(),ResponseCode.NOT_AUTHORITY.getMsg());
           }
           return iProductService.addOrUpdate(product);
       }
       return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 产品上下架
     */
    @RequestMapping(value = "set_sale_status.do")
    public ServerReponse set_sale_status(HttpSession session,Integer productId,Integer status){
        Object object = session.getAttribute(Const.CURRENTUSER);
        if (object != null &&  object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
            //判断用户权限
            if (userInfo.getRole() != Const.USER_ROLE_ADMIN){
                return ServerReponse.createServerResponseByError(ResponseCode.NOT_AUTHORITY.getStatus(),ResponseCode.NOT_AUTHORITY.getMsg());
            }
            return iProductService.addset_sale_status(productId,status);
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 查看商品详情
     */
    @RequestMapping(value = "detail.do")
    public ServerReponse detail(HttpSession session,Integer productId){
        Object object = session.getAttribute(Const.CURRENTUSER);
        if (object != null &&  object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
            //判断用户权限
            if (userInfo.getRole() != Const.USER_ROLE_ADMIN){
                return ServerReponse.createServerResponseByError(ResponseCode.NOT_AUTHORITY.getStatus(),ResponseCode.NOT_AUTHORITY.getMsg());
            }
            return iProductService.detail(productId);
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 查询商品列表
     */
    @RequestMapping(value = "list.do")
    public ServerReponse list(HttpSession session,
                              @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object object = session.getAttribute(Const.CURRENTUSER);
        if (object != null &&  object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
            //判断用户权限
            if (userInfo.getRole() != Const.USER_ROLE_ADMIN){
                return ServerReponse.createServerResponseByError(ResponseCode.NOT_AUTHORITY.getStatus(),ResponseCode.NOT_AUTHORITY.getMsg());
            }
            return iProductService.list(pageNum,pageSize);
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

    /**
     * 产品搜索
     */
    @RequestMapping(value = "search.do")
    public ServerReponse search(HttpSession session,
                                @RequestParam(value = "productId",required = false) Integer productId,
                                @RequestParam(value = "productName",required = false) String productName,
                                @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){
        Object object = session.getAttribute(Const.CURRENTUSER);
        if (object != null &&  object instanceof UserInfo){
            UserInfo userInfo = (UserInfo) object;
            //判断用户权限
            if (userInfo.getRole() != Const.USER_ROLE_ADMIN){
                return ServerReponse.createServerResponseByError(ResponseCode.NOT_AUTHORITY.getStatus(),ResponseCode.NOT_AUTHORITY.getMsg());
            }
            return iProductService.search(productId,productName,pageNum,pageSize);
        }
        return ServerReponse.createServerResponseByError(ResponseCode.USER_NOT_LOGIN.getStatus(),ResponseCode.USER_NOT_LOGIN.getMsg());
    }

}
