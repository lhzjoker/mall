package com.imooc.mimall.pojo;

import lombok.Data;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/4 14:34
 */
@Data
public class Cart {
    private Integer productId;

    private Integer quantity;   //购物车购买数量

    private Boolean productSelected;

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }

    public Cart() {
    }
}
