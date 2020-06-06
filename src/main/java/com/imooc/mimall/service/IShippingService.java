package com.imooc.mimall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.form.ShippingForm;
import com.imooc.mimall.vo.ResponseVo;

import java.util.Map;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/5 20:51
 */
public interface IShippingService {
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm shippingForm);

    public ResponseVo delete(Integer shippingId, Integer uid);

    public ResponseVo update(Integer shippingId, Integer uid, ShippingForm shippingForm);

    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
