package com.neuedu.controller.portal;

import com.neuedu.common.ServerReponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/portal/product/")
public class ProductController {

    @Autowired
    IProductService iProductService;

    /**
     * 前台--查看商品详情
     */
    @RequestMapping(value = "detail.do")
    public ServerReponse detail(Integer productId){
        return iProductService.detail_portal(productId);
    }

    /**
     * 前台--产品搜索及动态排序List
     */
    @RequestMapping(value = "list.do")
    public ServerReponse list(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                              @RequestParam(value = "keyword",required = false) String keyword,
                              @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                              @RequestParam(value = "orderBy",required = false,defaultValue = "") String orderBy){
        return iProductService.list_portal(categoryId, keyword, pageNum, pageSize, orderBy);
    }

}
