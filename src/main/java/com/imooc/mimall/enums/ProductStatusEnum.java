package com.imooc.mimall.enums;

import lombok.Getter;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/2 16:49
 */
@Getter
//商品状态.1-在售 2-下架 3-删除
public enum ProductStatusEnum {

    ON_SALE(1),

    OFF_SALE(2),

    DELETE(3);

    private Integer code;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
