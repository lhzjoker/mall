package com.imooc.mimall.service.Impl;

import com.imooc.mimall.MimallApplicationTests;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.service.ICategoryService;
import com.imooc.mimall.vo.CategoryVo;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/1 23:31
 */
@Slf4j
public class CategoryServiceImplTest extends MimallApplicationTests {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVo<List<CategoryVo>> responseVo = categoryService.selectAll();

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void findSubCategoryId() {
        Set<Integer> set = new HashSet<>();
        categoryService.findSubCategoryId(100001, set);
        log.info("set = {}",set);
    }
}