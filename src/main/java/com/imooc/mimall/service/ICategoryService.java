package com.imooc.mimall.service;

import com.imooc.mimall.vo.CategoryVo;
import com.imooc.mimall.vo.ResponseVo;

import java.util.List;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/1 11:47
 */
public interface ICategoryService {
    public ResponseVo<List<CategoryVo>> selectAll();
}
