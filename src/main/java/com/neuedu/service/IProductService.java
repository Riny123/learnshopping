package com.neuedu.service;

import com.neuedu.common.ServerReponse;
import com.neuedu.pojo.Product;
import org.springframework.web.multipart.MultipartFile;


public interface IProductService {

    /**
     * 增加商品或更新商品
     */
    ServerReponse addOrUpdate(Product product);

    /**
     * 商品上下架
     */
    ServerReponse addset_sale_status(Integer productId,Integer status);

    /**
     * 查看商品详情
     */
    ServerReponse detail(Integer productId);

    /**
     * 查看商品列表
     */
    ServerReponse list(Integer pageNum,Integer pageSize);

    /**
     * 后台--搜索商品
     */
    ServerReponse search(Integer productId,String productName,Integer pageNum,Integer pageSize);

    /**
     * 图片上传
     */
    /*
    * MultipartFile要传的文件
    * path文件上传的路径
    * */
    ServerReponse upload(MultipartFile file,String path);

    /**
     * 前台--查看商品详情
     */
    ServerReponse detail_portal(Integer productId);

    /**
     * 前台--产品搜索及动态排序List
     */
    ServerReponse list_portal(Integer categoryId,String keyword,Integer pageNum,Integer pageSize,String orderBy);

}
