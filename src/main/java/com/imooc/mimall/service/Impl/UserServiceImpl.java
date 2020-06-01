package com.imooc.mimall.service.Impl;

import com.imooc.mimall.dao.UserMapper;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.enums.RoleEnum;
import com.imooc.mimall.pojo.User;
import com.imooc.mimall.service.IUserService;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/24 18:23
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {
        //用户名不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
           return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        //邮箱不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
           return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        user.setRole(RoleEnum.Customer.getCode());
        //MD5摘要算法（加密）springboot自带
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        //写入数据库
        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
           return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.successByMsg("注册成功");
    }

    @Override
    public ResponseVo<User> login(String username,String password) {
        User user = userMapper.selectByUsername(username);
        if(user==null){
            //用户名不存在
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        if(!user.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            //密码错误
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        //不返回密码
        user.setPassword("");
        return  ResponseVo.success(user,"登录成功");
    }
}
