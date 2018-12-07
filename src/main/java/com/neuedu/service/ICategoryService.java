package com.neuedu.service;

import com.neuedu.common.ServerReponse;

public interface ICategoryService {

    /**
     * 获取类别子节点（平级）
     */
    ServerReponse get_category(String categoryId);

}
