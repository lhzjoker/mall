package com.imooc.mimall.service;

import com.imooc.mimall.form.CartAddForm;
import com.imooc.mimall.form.CartUpdateForm;
import com.imooc.mimall.vo.CartVo;
import com.imooc.mimall.vo.ResponseVo;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/4 0:14
 */
public interface ICartService {
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);

    public ResponseVo<CartVo> list(Integer uid);

    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);

    public ResponseVo<CartVo> delete(Integer uid, Integer productId);

    public ResponseVo<CartVo> selectAll(Integer uid);

    public ResponseVo<CartVo> unSelectAll(Integer uid);

    public ResponseVo<Integer> sum(Integer uid);

}
