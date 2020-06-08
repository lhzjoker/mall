package com.imooc.mimall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/8 22:54
 */
@Data
public class OrderCreateForm {
    @NotNull
    private Integer shippingId;
}
