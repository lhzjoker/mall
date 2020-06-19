package com.imooc.mimall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/15 18:16
 */
@Data
public class ProductUpdateForm {
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
