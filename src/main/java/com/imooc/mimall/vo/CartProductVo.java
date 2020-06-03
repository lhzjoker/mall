package com.imooc.mimall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/3 23:31
 */
@Data
public class CartProductVo {

    private Integer productId;

    private Integer quantity;   //购物车购买数量

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    private BigDecimal productTotalPrice;

    private Integer productStock;   //库存

    private Boolean productSelected;
}
