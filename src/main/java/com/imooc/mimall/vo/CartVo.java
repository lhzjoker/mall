package com.imooc.mimall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/3 23:28
 */
@Data
public class CartVo {
    private List<CartProductVo> cartProductVoList;

    private  Boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
