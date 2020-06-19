package com.imooc.mimall.service.Impl;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.MimallApplicationTests;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.form.ProductAddForm;
import com.imooc.mimall.form.ProductUpdateForm;
import com.imooc.mimall.service.IProductService;
import com.imooc.mimall.vo.ProductDetailVo;
import com.imooc.mimall.vo.ProductVo;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

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

    @Test
    public void add() {
        ProductAddForm productAddForm = new ProductAddForm();
        productAddForm.setCategoryId(100012);
        productAddForm.setId(30);
        productAddForm.setMainImage("https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/f515ab05232ed14ccd78ec67e024495a.png");
        productAddForm.setName("Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机");
        productAddForm.setPrice(BigDecimal.valueOf(1799));
        productAddForm.setStatus(1);
        productAddForm.setStock(100);
        productAddForm.setSubtitle("3200万+4800万 前后双旗舰相机");
        ResponseVo<ProductVo> responseVo = productService.add(productAddForm);
        log.info("add = {}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void update() {
        ProductUpdateForm productUpdateForm = new ProductUpdateForm();
        productUpdateForm.setCategoryId(100012);
        productUpdateForm.setMainImage("https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/f515ab05232ed14ccd78ec67e024495a.png");
        productUpdateForm.setName("Apple iPhone 7 Plus (A1661) 128G 玫瑰金色 移动联通电信4G手机");
        productUpdateForm.setPrice(BigDecimal.valueOf(1799));
        productUpdateForm.setStatus(0);
        productUpdateForm.setStock(99);
        productUpdateForm.setSubtitle("3200万+4800万 前后双旗舰相机");
        ResponseVo<ProductVo> update = productService.update(26,productUpdateForm);
        log.info("add = {}",update);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),update.getStatus());
    }

    @Test
    public void delete() {
        ResponseVo delete = productService.delete(26);
        log.info("add = {}",delete);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),delete.getStatus());
    }
}