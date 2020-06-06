package com.imooc.mimall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/5 20:50
 */
@Data
public class ShippingForm {
    @NotBlank
    private String receiverName;
    @NotBlank
    private String receiverPhone;
    @NotBlank
    private String receiverMobile;
    @NotBlank
    private String receiverProvince;
    @NotBlank
    private String receiverCity;
    @NotBlank
    private String receiverDistrict;
    @NotBlank
    private String receiverAddress;
    @NotBlank
    private String receiverZip;

    public ShippingForm() {
    }
}
