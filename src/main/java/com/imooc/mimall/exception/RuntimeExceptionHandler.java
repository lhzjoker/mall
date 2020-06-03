package com.imooc.mimall.exception;

import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/25 23:14
 */
@ControllerAdvice       //全局异常处理 包含@Component。可以被扫描到。统一处理异常。
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)   //用在方法上面表示遇到这个异常就执行以下方法
    @ResponseBody
    //@ResponseStatus(HttpStatus.ACCEPTED)    //设置返回状态码
    public ResponseVo handle(RuntimeException e){
        return ResponseVo.error(ResponseEnum.ERROR,e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)   //用在方法上面表示遇到这个异常就执行以下方法
    @ResponseBody
    public ResponseVo userLoginHandle(RuntimeException e){
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo notValidExceptionHandle(MethodArgumentNotValidException e){
        //e可以拿到bindingResult
        BindingResult bindingResult = e.getBindingResult();
        return ResponseVo.error(ResponseEnum.PARAMS_ERROR,bindingResult);
    }
}
