package com.imooc.mimall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/3 23:42
 */
@Data
public class CartAddForm {
    @NotNull    //Integer类型要用notnull
    private Integer productId;

    private Boolean selected = true;
}
