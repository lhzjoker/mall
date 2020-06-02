package com.imooc.mimall.service.Impl;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.MimallApplicationTests;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.service.IProductService;
import com.imooc.mimall.vo.ProductDetailVo;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/2 14:05
 */
@Slf4j
public class ProductServiceimplTest extends MimallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        ResponseVo<PageInfo> list = productService.list(null, 2, 3);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),list.getStatus());
    }

    @Test
    public void productDetail() {
        ResponseVo<ProductDetailVo> productDetailVo = productService.productDetail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),productDetailVo.getStatus());
    }
}