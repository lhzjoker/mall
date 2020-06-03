package com.imooc.mimall.enums;

import lombok.Getter;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/25 14:47
 */
@Getter
public enum ResponseEnum {

    ERROR(-1, "服务端错误"),

    SUCCESS(0, "成功"),

    PASSWORD(1, "密码错误"),

    USERNAME_EXIST(2, "用户名已存在"),

    PARAMS_ERROR(3,"参数错误"),

    EMAIL_EXIST(4, "邮箱已存在"),

    NEED_LOGIN(10, "用户未登陆，请先登陆"),

    USERNAME_OR_PASSWORD_ERROR(11,"用户名不存在或者密码错误"),

    PRODUCT_OFF_SALE_OR_DELETE(12,"商品下架或者删除"),

    PRODUCT_NOT_EXIST(13,"商品不存在"),

    PRODUCT_STOCK_ERROR(14,"库存不存在");

    private Integer code;

    private String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
