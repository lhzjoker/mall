package com.imooc.mimall.service;

import com.imooc.mimall.form.CartAddForm;
import com.imooc.mimall.vo.CartVo;
import com.imooc.mimall.vo.ResponseVo;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/4 0:14
 */
public interface ICartService {
    public ResponseVo<CartVo> add(CartAddForm cartAddForm);
}
