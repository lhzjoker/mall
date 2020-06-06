package com.imooc.mimall.Controllerr;

import com.imooc.mimall.consts.MallConst;
import com.imooc.mimall.form.CartAddForm;
import com.imooc.mimall.form.CartUpdateForm;
import com.imooc.mimall.pojo.User;
import com.imooc.mimall.service.ICartService;
import com.imooc.mimall.vo.CartVo;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/3 23:47
 */
@RestController

public class CartController {

    @Autowired
    private ICartService cartService;


    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> list = cartService.list(user.getId());
        return list;
    }

    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> addList = cartService.add(user.getId(), cartAddForm);
        return addList;
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable("productId") Integer productId, @RequestBody CartUpdateForm cartUpdateForm, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> updateList = cartService.update(user.getId(), productId, cartUpdateForm);
        return updateList;
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable("productId") Integer productId, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> deleteList = cartService.delete(user.getId(), productId);
        return deleteList;
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> responseVo = cartService.selectAll(user.getId());
        return responseVo;
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(user.getId());
        return responseVo;
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<Integer> sum = cartService.sum(user.getId());
        return sum;
    }
}
