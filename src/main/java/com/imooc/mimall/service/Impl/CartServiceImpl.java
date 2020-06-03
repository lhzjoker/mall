package com.imooc.mimall.service.Impl;

import com.imooc.mimall.dao.ProductMapper;
import com.imooc.mimall.enums.ProductStatusEnum;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.form.CartAddForm;
import com.imooc.mimall.pojo.Product;
import com.imooc.mimall.service.ICartService;
import com.imooc.mimall.vo.CartVo;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/4 0:15
 */
public class CartServiceImpl implements ICartService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<CartVo> add(CartAddForm cartAddForm) {

        //商品是否存在
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        if(product == null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否在售
        if(!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品库存是否充足
        if(product.getStock()<=0){
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        return null;
    }
}
