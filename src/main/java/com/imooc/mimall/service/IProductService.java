package com.imooc.mimall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.form.ProductAddForm;
import com.imooc.mimall.vo.ProductDetailVo;
import com.imooc.mimall.vo.ProductVo;
import com.imooc.mimall.vo.ResponseVo;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/1 22:55
 */
public interface IProductService {
    public ResponseVo<ProductDetailVo> productDetail(Integer productId);

    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    public ResponseVo<ProductVo> add(ProductAddForm productAddForm);
}
