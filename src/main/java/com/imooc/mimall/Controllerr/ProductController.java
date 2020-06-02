package com.imooc.mimall.Controllerr;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.service.IProductService;
import com.imooc.mimall.vo.ProductDetailVo;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/1 23:02
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseVo<ProductDetailVo> productDetail(@PathVariable("productId") Integer productId) {
        return productService.productDetail(productId);
    }

    @GetMapping("/products")
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        ResponseVo<PageInfo> list = productService.list(categoryId, pageNum, pageSize);
        return list;
    }
}
