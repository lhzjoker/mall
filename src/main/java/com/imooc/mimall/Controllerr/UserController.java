package com.imooc.mimall.Controllerr;

import com.imooc.mimall.consts.MallConst;
import com.imooc.mimall.form.UserLoginForm;
import com.imooc.mimall.form.UserRegisterForm;
import com.imooc.mimall.pojo.User;
import com.imooc.mimall.service.IUserService;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/25 0:10
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userForm) {

        //如果出现错误，注意这里进行了统一错误拦截
        //bindingResult.getFieldError().getField()获取字段
        //bindingResult.getFieldError().getDefaultMessage()获取默认错误信息
//        if (bindingResult.hasErrors()) {
//            log.error("注册提交的参数有误，{} {}", bindingResult.getFieldError().getField(),
//                    bindingResult.getFieldError().getDefaultMessage());
//            return ResponseVo.error(ResponseEnum.PARAMS_ERROR, bindingResult);
//        }

        User user = new User();
        BeanUtils.copyProperties(userForm, user);       //把userForm的内容copy给user
        return userService.register(user);
    }

    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm, HttpSession session) {

        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        //设置session，session保存在内存中，重启就没了，改进版：token+redis
        session.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());

        //打印sessionId
        log.info("/login sessionId={}", session.getId());
        return userResponseVo;
    }

    @GetMapping("/user")
    public ResponseVo<User> UserInfo(HttpSession session) {
        //打印sessionId
        log.info("/user sessionId={}", session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success(user);
    }


    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session) {
        log.info("/user/logout sessionId={}", session.getId());
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.successByMsg("登出成功");
    }

    private void error() {
        throw new RuntimeException("意外错误");
    }
}
