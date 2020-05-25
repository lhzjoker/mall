package com.imooc.mimall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.mimall.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/25 14:44
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)      //json序列化的时候去掉null
public class ResponseVo<T> {
    private Integer status;

    private String msg;

    private T data;

    public ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static <T> ResponseVo<T> success(String msg){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),msg);
    }

    public static <T> ResponseVo<T> success(){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo<T>(responseEnum.getCode(),responseEnum.getDesc());
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum,String msg){
        return new ResponseVo<T>(responseEnum.getCode(),msg);
    }

    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseVo<T>(responseEnum.getCode(), Objects.requireNonNull(bindingResult.getFieldError()).getField()+" "+
                bindingResult.getFieldError().getDefaultMessage());
    }
}
