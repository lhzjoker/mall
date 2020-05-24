package com.imooc.mimall.service;

import com.imooc.mimall.pojo.User;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/24 18:20
 */
public interface IUserService {
    //注册
    void register(User user);
    //登陆
    void login(User user);
}
