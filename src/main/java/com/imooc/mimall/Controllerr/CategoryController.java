package com.imooc.mimall.Controllerr;

import com.imooc.mimall.service.ICategoryService;
import com.imooc.mimall.vo.CategoryVo;
import com.imooc.mimall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/1 12:38
 */
@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll(){
       return categoryService.selectAll();
    }
}
