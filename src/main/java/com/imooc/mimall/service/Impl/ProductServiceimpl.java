package com.imooc.mimall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mimall.dao.ProductMapper;
import com.imooc.mimall.enums.ProductStatusEnum;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.form.ProductAddForm;
import com.imooc.mimall.pojo.Product;
import com.imooc.mimall.service.ICategoryService;
import com.imooc.mimall.service.IProductService;
import com.imooc.mimall.vo.ProductDetailVo;
import com.imooc.mimall.vo.ProductVo;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/1 22:58
 */
@Service
public class ProductServiceimpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<ProductDetailVo> productDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())||product.getStatus().equals(ProductStatusEnum.DELETE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo =new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        productDetailVo.setStock(product.getStock()>100?100:productDetailVo.getStock());    //设置虚假库存，让别人只看到一部分
        return ResponseVo.success(productDetailVo);
    }


    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }

        //使用mybatis分页器，pageNum表示第几页，pageSize表示每页的条数
        PageHelper.startPage(pageNum,pageSize);
        //返回值赋值成productVo类型
        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList = productList.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo<>(productList);    //所有查到的数据
        pageInfo.setList(productVoList);

        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<ProductVo> add(ProductAddForm productAddForm) {
        if(productMapper.selectByPrimaryKey(productAddForm.getId())!=null){
            return ResponseVo.error(ResponseEnum.PRODUCT_EXIST);
        }
        Product product = new Product();
        BeanUtils.copyProperties(productAddForm,product);
        int row = productMapper.insertSelective(product);
        if(row<=0){
            ResponseVo.error(ResponseEnum.ERROR);
        }
        Product product1 = productMapper.selectByPrimaryKey(productAddForm.getId());
        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(product1,productVo);
        return ResponseVo.success(productVo,"添加商品成功");
    }


}
