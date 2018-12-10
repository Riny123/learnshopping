package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerReponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.PropertiesUtil;
import com.neuedu.vo.ProductDetailVO;
import com.neuedu.vo.ProductListVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ICategoryService iCategoryService;

    /**
     * 增加商品或更新商品
     */
    @Override
    public ServerReponse addOrUpdate(Product product) {
        //step1:参数的非空校验
        if (product == null){
            return ServerReponse.createServerResponseByError(ResponseCode.PARAM_EMPTY.getStatus(),ResponseCode.PARAM_EMPTY.getMsg());
        }
        //step2:设置商品主图   sub_image --> 从子图中取出第一张作为主图
        //首先先拿到子图
        String sub_image = product.getSubImage();
        if (!StringUtils.isBlank(sub_image)){
            //得到子图的字符串数组
            String[] subImages = sub_image.split(",");
            //判断子图的字符串是否为空，如果为空，为下标越界
            if (subImages.length > 0){
                //设置主图为子图数组的第一个元素
                product.setMainImage(subImages[0]);
            }
        }
        //step3:判断是对商品进行添加还是更新
        if (product.getId() == null){
            //如果商品id为空，则为商品添加
            int add_result = productMapper.insert(product);
            if(add_result <= 0){
                return ServerReponse.createServerResponseByError("商品添加失败");
            }
            return ServerReponse.createServerResponseBySuccess("商品添加成功");
        }else {
            //如果商品id不为空，则为更新
            int update_result = productMapper.updateByPrimaryKey(product);
            if(update_result <= 0){
                return ServerReponse.createServerResponseByError("商品更新失败");
            }
            return ServerReponse.createServerResponseBySuccess("商品更新成功");
        }
    }

    /**
     * 商品上下架
     */
    @Override
    public ServerReponse addset_sale_status(Integer productId, Integer status) {
        //step1:参数的非空校验：produceId status
        if (productId == null || status == null){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_PARAM_EMPTY.getStatus(),ResponseCode.NOT_PARAM_EMPTY.getMsg());
        }
        //step2:更新商品状态
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int result = productMapper.updateProductStatus(product);
        if (result <= 0){
            return ServerReponse.createServerResponseByError("更新状态失败");
        }
        //step3:返回结果
        return ServerReponse.createServerResponseBySuccess("更新状态成功");
    }

    /**
     *查看商品详情
     */
    @Override
    public ServerReponse detail(Integer productId) {
        //step1:参数的非空校验
        if (productId == null){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_PARAM_EMPTY.getStatus(),ResponseCode.NOT_PARAM_EMPTY.getMsg());
        }
        //step2:根据productId查询商品
        Product product = productMapper.selectByPrimaryKey(productId);
        //进行非空判断
        if (product == null){
            return ServerReponse.createServerResponseByError("该商品不存在");
        }
        //step3:把product转换为productDetailVO
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);
        //step4:返回结果
        return ServerReponse.createServerResponseBySuccess(productDetailVO,null);
    }

    //把Product转换为ProductDetailVO的方法
    private ProductDetailVO assembleProductDetailVO(Product product){
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setId(product.getId());
        productDetailVO.setCategoryId(product.getCategoryId());

        //设置ParentCategoryId
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category != null){
            productDetailVO.setParentCategoryId(category.getParentId());
        }else {
            //默认为根节点
            productDetailVO.setParentCategoryId(0);
        }

        productDetailVO.setName(product.getName());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setImageHost(PropertiesUtil.readByKey("imageHost"));
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setSubImages(product.getSubImage());
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setCreateTime(DateUtil.dateToStr(product.getCreateTime()));
        productDetailVO.setUpdateTime(DateUtil.dateToStr(product.getUpdateTime()));
        return productDetailVO;
    }

    /**
     * 查看商品列表
     */
    @Override
    public ServerReponse list(Integer pageNum, Integer pageSize) {
        //在查询信息之前用分页插件
        PageHelper.startPage(pageNum,pageSize);
        //step1:查询商品数据
        List<Product> productList = productMapper.selectAll();
        //定义一个新的集合来接收
        List<ProductListVO> productListVOList = new ArrayList<>();
        if (productList != null && productList.size() > 0){
            for (Product product:productList) {
                ProductListVO productListVO = assembleProductListVO(product);
                productListVOList.add(productListVO);
            }
        }
        //step2:返回结果
        //返回的是一个分页集合
        PageInfo pageInfo = new PageInfo(productListVOList);
        return ServerReponse.createServerResponseBySuccess(pageInfo,null);
    }

    //把Product转换为ProductListVO的方法
    private ProductListVO assembleProductListVO(Product product){
        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setName(product.getName());
        productListVO.setSubtitle(product.getSubtitle());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setStatus(product.getStatus());
        productListVO.setPrice(product.getPrice());
        return productListVO;
    }

    /**
     * 后台--搜索商品
     */
    @Override
    public ServerReponse search(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        //因为参数都不是必须的，所以可以省略非空校验
        //分页
        PageHelper.startPage(pageNum,pageSize);
        //判断productName不为空是进行模糊查询
        if (!StringUtils.isBlank(productName)){
            productName = "%"+productName+"%";
        }
        List<ProductListVO> productListVOList = new ArrayList<>();
        List<Product> productList = productMapper.findProductByProductIdAndProductName(productId,productName);
        if (productList != null && productList.size() > 0){
            for (Product product:productList) {
                ProductListVO productListVO = assembleProductListVO(product);
                productListVOList.add(productListVO);
            }
        }
        PageInfo pageInfo = new PageInfo(productListVOList);
        return ServerReponse.createServerResponseBySuccess(pageInfo,null);
    }

    /**
     * 图片上传
     */
    @Override
    public ServerReponse upload(MultipartFile file, String path) {
        //step1:参数的非空校验
        if (file == null){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_PARAM_EMPTY.getStatus(),ResponseCode.NOT_PARAM_EMPTY.getMsg());
        }
        //step2:获取原来图片的名字
        String oldFileName = file.getOriginalFilename();
        //step3:获取图片的扩展名: 会截到.
        String exName = oldFileName.substring(oldFileName.lastIndexOf("."));
        //step4:为图片生成新的唯一的名字
        String newFileName = UUID.randomUUID().toString() + exName;
        //step5:new一个File
        File pathFile = new File(path);
        //判断path路径下文件是否存在
        if (!pathFile.exists()){
            //让文件可写，可操作
            pathFile.setWritable(true);
            //生成这个目录
            pathFile.mkdir();
        }
        //把文件写在上面创建的目录下
        File file1 = new File(path,newFileName);
        try {
            //把新生成的文件写入file中
            file.transferTo(file1);

            //上传到图片服务器。。。

            Map<String ,String> map = Maps.newHashMap();
            map.put("uri",newFileName);
            map.put("url",PropertiesUtil.readByKey("imageHost")+"/"+newFileName);
            return ServerReponse.createServerResponseBySuccess(map,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerReponse.createServerResponseByError("图片上传失败");
    }

    /**
     * 前台--查看商品详情
     */
    @Override
    public ServerReponse detail_portal(Integer productId) {
        //step1:参数的非空校验
        if (productId == null){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_PARAM_EMPTY.getStatus(),ResponseCode.NOT_PARAM_EMPTY.getMsg());
        }
        //step2:根据商品id去查询商品
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerReponse.createServerResponseByError("该商品不存在");
        }
        //step3:校验商品的状态
        //获取当前商品的状态
        Integer status = product.getStatus();
        if (status == Const.PRODUCT_STATUS_DOWN || status == Const.PRODUCT_STATUS_DELETE){
            return ServerReponse.createServerResponseByError("该商品已下架或移除");
        }
        //step4:获取productDetailVO
        ProductDetailVO productDetailVO = assembleProductDetailVO(product);
        //step5:返回结果
        return ServerReponse.createServerResponseBySuccess(productDetailVO,null);
    }

    /**
     * 前台--产品搜索及动态排序List
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @Override
    public ServerReponse list_portal(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        //step1:参数的非空校验，categoryId和keyword不能同时为空
        if (categoryId == null && StringUtils.isBlank(keyword)){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_PARAM_EMPTY.getStatus(),ResponseCode.NOT_PARAM_EMPTY.getMsg());
        }
        //step2:categoryId进行查询
        Set<Integer> integerSet = Sets.newHashSet();
        if (categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)){//说明没有商品数据
                //还是先进行分页
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVO> productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return ServerReponse.createServerResponseBySuccess(pageInfo,null);
            }
            //categoryId不等于空，查找类别下面的子节点
            ServerReponse serverReponse = iCategoryService.get_deep_category(categoryId);
            if (serverReponse.isSuccess()){
                integerSet = (Set<Integer>) serverReponse.getData();
            }
        }
        //step3:keyword进行查询
        if (!StringUtils.isBlank(keyword)){
            keyword = "%"+keyword+"%";
        }
        if (StringUtils.isBlank(orderBy)){
            PageHelper.startPage(pageNum,pageSize);
        }else{
            String[] orderByArr = orderBy.split("_");
            if (orderByArr.length > 0){
                PageHelper.startPage(pageNum,pageSize,orderByArr[0]+" "+orderByArr[1]);
            }else {
                PageHelper.startPage(pageNum,pageSize);
            }
        }
        List<Product> productList = productMapper.findProduct(integerSet,keyword);
        List<ProductListVO> productListVOList = new ArrayList<>();
        //step4:List<Product> --> List<ProductListVO>
        if (productList != null && productList.size() > 0){
            for (Product product:productList) {
                ProductListVO productListVO = assembleProductListVO(product);
                productListVOList.add(productListVO);
            }
        }
        //step5:分页
        PageInfo pageInfo = new PageInfo(productListVOList);
        //step6:返回结果
        return ServerReponse.createServerResponseBySuccess(pageInfo,null);
    }
}