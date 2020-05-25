package com.imooc.mimall.service;

import com.imooc.mimall.pojo.User;
import com.imooc.mimall.vo.ResponseVo;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/24 18:20
 */
public interface IUserService {
    //注册
    public ResponseVo register(User user);

    //登陆
    public ResponseVo login(User user);
}
