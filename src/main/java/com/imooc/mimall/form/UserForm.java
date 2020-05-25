package com.imooc.mimall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/25 18:52
 * 表单验证
 */
@Data
public class UserForm {

    //@NotNull 判断值是否为null
    //@NotEmpty 用于集合
    //NotBlank 用于String 判断空格


    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
