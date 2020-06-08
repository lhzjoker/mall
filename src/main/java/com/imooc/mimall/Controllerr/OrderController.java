package com.imooc.mimall.Controllerr;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.consts.MallConst;
import com.imooc.mimall.form.OrderCreateForm;
import com.imooc.mimall.pojo.User;
import com.imooc.mimall.service.IOrderService;
import com.imooc.mimall.vo.OrderVo;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/8 21:06
 */
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(HttpSession session,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(HttpSession session, @Valid @RequestBody OrderCreateForm orderCreateForm) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(user.getId(), orderCreateForm.getShippingId());
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(HttpSession session, @PathVariable("orderNo") Long orderNo) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(),orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(HttpSession session,@PathVariable("orderNo") Long orderNo){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(),orderNo);
    }
}
