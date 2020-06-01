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
public class UserLoginForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
