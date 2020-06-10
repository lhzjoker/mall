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

    PRODUCT_STOCK_ERROR(14,"库存不正确"),

    CART_PRODUCT_NOT_EXIST(15,"购物车里无此商品"),

    SHIPPINGS_NOT_EXIST(16,"抱歉，您暂时无已添加的收获地址"),

    DELETE_SHIPPING_FAIL(17,"删除地址失败"),

    SHIPPING_NOT_EXIST(18,"此收获地址不存在"),

    CART_SELECTED_IS_EMPTY(19,"请选择商品后再下单"),

    ORDER_NOT_EXIST(20,"订单不存在"),

    ORDER_STATUS_ERROR(21,"订单状态错误"),

    ;

    private Integer code;

    private String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
