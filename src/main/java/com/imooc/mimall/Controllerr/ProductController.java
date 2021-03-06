package com.imooc.mimall.Controllerr;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.form.ProductAddForm;
import com.imooc.mimall.form.ProductUpdateForm;
import com.imooc.mimall.service.IProductService;
import com.imooc.mimall.vo.ProductDetailVo;
import com.imooc.mimall.vo.ProductVo;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/products")
    public ResponseVo<ProductVo> add(@Valid @RequestBody ProductAddForm productAddForm){
        return productService.add(productAddForm);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseVo delete(@PathVariable("productId") Integer productId){
        return productService.delete(productId);
    }

    @PostMapping("/products/{productId}")
    public ResponseVo update(@PathVariable("productId") Integer productId,@Valid @RequestBody ProductUpdateForm productUpdateForm){
        return productService.update(productId,productUpdateForm);
    }
}
