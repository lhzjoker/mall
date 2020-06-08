package com.imooc.mimall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.vo.OrderVo;
import com.imooc.mimall.vo.ResponseVo;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/7 19:09
 */
public interface IOrderService {
    public ResponseVo<OrderVo> create(Integer uid,Integer shippingId);

    public ResponseVo<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);

    public ResponseVo<OrderVo> detail(Integer uid,Long orderNo);

    public ResponseVo cancel(Integer uid,Long orderNo);
}
