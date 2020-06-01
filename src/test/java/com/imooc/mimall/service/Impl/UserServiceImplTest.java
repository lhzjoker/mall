package com.imooc.mimall.service.Impl;

import com.imooc.mimall.MimallApplicationTests;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.enums.RoleEnum;
import com.imooc.mimall.pojo.User;
import com.imooc.mimall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/24 18:59
 */
@Transactional      //事务回滚功能，不会写入数据库中
public class UserServiceImplTest extends MimallApplicationTests {

    public static final String USERNAME = "jack";
    public static final String PASSWORD = "jack";

    @Autowired
    private UserServiceImpl userService;

    @Before     //测试方法执行前需执行的方法（登录之前需要注册）
    public void register() {
        userService.register(new User(USERNAME,PASSWORD,"123@qq.com", RoleEnum.Customer.getCode()));
    }

    @Test
    public void login(){
        ResponseVo<User> user = userService.login(USERNAME, PASSWORD);
        //判断有没有登录成功
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),user.getStatus());   //期望的和实际的，登录成功期望的为0
    }
}