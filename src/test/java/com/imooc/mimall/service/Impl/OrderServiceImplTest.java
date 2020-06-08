package com.imooc.mimall.service.Impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mimall.MimallApplicationTests;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.form.CartAddForm;
import com.imooc.mimall.service.ICartService;
import com.imooc.mimall.service.IOrderService;
import com.imooc.mimall.vo.CartVo;
import com.imooc.mimall.vo.OrderVo;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/7 20:19
 */
@Slf4j
@Transactional
public class OrderServiceImplTest extends MimallApplicationTests {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Integer uid = 1;

    private Integer shippingId = 5;

    private Integer productId = 29;

//    private Long orderNo = 1591592671273L;      //long类型数据初始化后面要加L


    @Before
    public void before() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(productId);
        ResponseVo<CartVo> responseVo = cartService.add(1, cartAddForm);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shippingId);
        log.info("responseVo = {}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    private ResponseVo<OrderVo> createTest(){
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shippingId);
        return responseVo;
    }

    @Test
    public void list() {
        ResponseVo<PageInfo> responseVo = orderService.list(uid, 1, 10);
        log.info("responseVo = {}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void detail() {
        ResponseVo<OrderVo> createTest = createTest();
        ResponseVo<OrderVo> responseVo = orderService.detail(uid, createTest.getData().getOrderNo());
        log.info("responseVo = {}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }

    @Test
    public void cancel() {
        ResponseVo<OrderVo> createTest = createTest();
        ResponseVo responseVo = orderService.cancel(uid, createTest.getData().getOrderNo());
        log.info("responseVo = {}", gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), responseVo.getStatus());
    }
}