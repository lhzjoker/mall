package com.imooc.mimall.form;

import lombok.Data;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/4 22:56
 */
@Data
public class CartUpdateForm {
    private Integer quantity;

    private Boolean selected;
}
