package com.neuedu.service.impl;

import com.google.common.collect.Sets;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerReponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 获取类别子节点（平级）
     * @param categoryId
     * @return
     */
    @Override
    public ServerReponse get_category(Integer categoryId) {
        //step1:参数的非空校验
        if (categoryId == null){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_PARAM_EMPTY.getStatus(),ResponseCode.NOT_PARAM_EMPTY.getMsg());
        }
        //step2:通过categoryId可以查询类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null){
            return ServerReponse.createServerResponseByError("查询类别不存在");
        }
        //step3:查询子类别
        List<Category> categories = categoryMapper.findChildCategory(categoryId);
        //step4:返回结果
        return ServerReponse.createServerResponseBySuccess(categories,null);
    }

    /**
     * 增加节点
     * @param parentId
     * @param categoryName
     * @return
     */
    @Override
    public ServerReponse add_category(Integer parentId, String categoryName) {
        //step1:参数的非空校验:因为parentId有默认值，肯定不为空，所以不用判断
        if (StringUtils.isBlank(categoryName)){
            return ServerReponse.createServerResponseByError("类别名称不能为空");
        }
        //step2:添加类别
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(1);
        int result = categoryMapper.insert(category);
        //step3:返回结果
        if (result <= 0){
            return ServerReponse.createServerResponseByError("添加失败");
        }
        return ServerReponse.createServerResponseBySuccess("添加成功");
    }

    /**
     * 修改类别名称
     */
    @Override
    public ServerReponse set_category_name(Integer categoryId, String categoryName) {
        //step1:参数的非空校验
        if (categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_PARAM_EMPTY.getStatus(),ResponseCode.NOT_PARAM_EMPTY.getMsg());
        }
        //step2:先用categoryId查询当前类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null){
            return ServerReponse.createServerResponseByError("修改的类别不存在");
        }
        //step3:修改类别名称
        category.setName(categoryName);
        int result = categoryMapper.updateByPrimaryKey(category);
        if (result <= 0){
            return ServerReponse.createServerResponseByError("修改失败");
        }
        //step4:返回结果
        return ServerReponse.createServerResponseBySuccess("修改成功");
    }

    /**
     * 获取当前分类id及递归子节点categoryId
     */
    @Override
    public ServerReponse get_deep_category(Integer categotyId) {
        //step1:参数的非空校验
        if (categotyId == null){
            return ServerReponse.createServerResponseByError(ResponseCode.NOT_PARAM_EMPTY.getStatus(),ResponseCode.NOT_PARAM_EMPTY.getMsg());
        }
        //step2:查询
        Set<Category> categorySet = Sets.newHashSet();
        categorySet = findAllChildCategory(categorySet,categotyId);
        Set<Integer> integerSet = Sets.newHashSet();
        Iterator<Category> iterator = categorySet.iterator();
        while (iterator.hasNext()){
            Category category = iterator.next();
            integerSet.add(category.getId());
        }
        return ServerReponse.createServerResponseBySuccess(integerSet,null);
    }

    //递归方法的编写
    private Set<Category> findAllChildCategory(Set<Category> categorySet,Integer categoryId){
        //查找本节点
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        //查找categoryId下的子节点（平级）
        List<Category> categoryList = categoryMapper.findChildCategory(categoryId);
        if (categoryList != null && categoryList.size() > 0){
            for (Category c:categoryList) {
                findAllChildCategory(categorySet,c.getId());
            }
        }
        return categorySet;
    }

}