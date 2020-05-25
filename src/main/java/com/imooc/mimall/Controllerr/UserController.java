package com.imooc.mimall.Controllerr;

import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.form.UserForm;
import com.imooc.mimall.pojo.User;
import com.imooc.mimall.service.IUserService;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/25 0:10
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserForm userForm, BindingResult bindingResult) {

//        error();

        //如果出现错误
        //bindingResult.getFieldError().getField()获取字段
        //bindingResult.getFieldError().getDefaultMessage()获取默认错误信息
        if (bindingResult.hasErrors()) {
            log.error("注册提交的参数有误，{} {}", bindingResult.getFieldError().getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAMS_ERROR, bindingResult);
        }

        User user = new User();
        BeanUtils.copyProperties(userForm,user);       //把userForm的内容copy给user
        return userService.register(user);
    }

    private void error(){
        throw new RuntimeException("意外错误");
    }
}
