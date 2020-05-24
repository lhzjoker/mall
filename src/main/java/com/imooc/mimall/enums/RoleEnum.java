package com.imooc.mimall.enums;

import lombok.Getter;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/5/24 20:13
 */
@Getter
public enum RoleEnum {
    Admin(0),
    Customer(1),;
    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
