package com.imooc.mimall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/13 19:40
 */
@Data
public class ProductAddForm {
    @NotNull
    private Integer id;
    @NotNull
    private Integer categoryId;
    @NotBlank
    private String name;
    @NotBlank
    private String subtitle;
    @NotBlank
    private String mainImage;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer stock;
    @NotNull
    private Integer status;
}
