package com.imooc.mimall.interceptor;

import com.imooc.mimall.consts.MallConst;
import com.imooc.mimall.exception.UserLoginException;
import com.imooc.mimall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/31 15:44
 */
//登陆拦截器
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    //请求之前拦截
    /**
     * true表示继续流程，false表示中断
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        //先判断有没有登陆
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if(user == null){
            log.info("user==null");
            //因为返回值只能是Boolean类型，所以用抛出异常的方式来返回body数据
            throw new UserLoginException();
        }
        return true;
    }
}
