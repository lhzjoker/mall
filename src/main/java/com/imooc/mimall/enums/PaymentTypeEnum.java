package com.imooc.mimall.enums;

import lombok.Getter;

/**
 *
 * @author lhz
 * @version 1.0
 * @date 2020/6/7 22:16
 */
@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1,"在线支付"),
    ;

    Integer code;

    String desc;

    PaymentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
