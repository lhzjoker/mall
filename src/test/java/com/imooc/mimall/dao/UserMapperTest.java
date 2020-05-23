package com.imooc.mimall.dao;

import com.imooc.mimall.MimallApplicationTests;
import com.imooc.mimall.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/22 18:36
 */
public class UserMapperTest extends MimallApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void insertSelective() {
    }

    @Test

    public void selectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(2);
        System.out.println(user.toString());
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void updateByPrimaryKey() {
    }
}