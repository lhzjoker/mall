package com.imooc.mimall.service.Impl;

import com.imooc.mimall.MimallApplicationTests;
import com.imooc.mimall.enums.RoleEnum;
import com.imooc.mimall.pojo.User;
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

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void register() {
        userService.register(new User("123","123","123@qq.com", RoleEnum.Customer.getCode()));
    }
}