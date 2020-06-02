package com.imooc.mimall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/2 0:50
 */
@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
