package com.imooc.mimall.Controllerr;

import com.imooc.mimall.form.CartAddForm;
import com.imooc.mimall.vo.CartVo;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/3 23:47
 */
@RestController

public class CartController {

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm){
        return  null;
    }
}
