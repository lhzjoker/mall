package com.imooc.mimall.service.Impl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mimall.MimallApplicationTests;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.form.ShippingForm;
import com.imooc.mimall.service.IShippingService;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/5 22:50
 */
@Slf4j
public class ShippingServiceImplTest extends MimallApplicationTests {

    private static Integer uid = 1;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Autowired
    private IShippingService shippingService;

    @Test
    public void add() {
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverAddress("家里蹲大学");
        shippingForm.setReceiverCity("娄底市");
        shippingForm.setReceiverDistrict("娄星区");
        shippingForm.setReceiverMobile("18711805370");
        shippingForm.setReceiverName("阿华");
        shippingForm.setReceiverPhone("123456789");
        shippingForm.setReceiverProvince("湖南省");
        shippingForm.setReceiverZip("417000");
        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, shippingForm);
        log.info("add = {}",gson.toJson(responseVo));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void delete() {
        ResponseVo delete = shippingService.delete(10,2);
        log.info("delete = {}",gson.toJson(delete));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),delete.getStatus());
    }

    @Test
    public void update() {
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverName("华爸爸");
        ResponseVo update = shippingService.update(6,1, shippingForm);
        log.info("update = {}",gson.toJson(update));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),update.getStatus());
    }

    @Test
    public void list() {
        ResponseVo<PageInfo> list = shippingService.list(uid, 1, 10);
        log.info("list = {}",gson.toJson(list));
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),list.getStatus());
    }
}