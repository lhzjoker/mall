package com.imooc.mimall.Controllerr;

import com.github.pagehelper.PageInfo;
import com.imooc.mimall.consts.MallConst;
import com.imooc.mimall.form.ShippingForm;
import com.imooc.mimall.pojo.User;
import com.imooc.mimall.service.IShippingService;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/6 0:39
 */
@RestController
public class ShippingController {
    @Autowired
    private IShippingService shippingService;

    @GetMapping("/shippings")
    public ResponseVo<PageInfo> list(HttpSession session,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        User user =(User) session.getAttribute(MallConst.CURRENT_USER);
        ResponseVo<PageInfo> list = shippingService.list(user.getId(), pageNum, pageSize);
        return list;
    }

    @PostMapping("/shippings")
    public ResponseVo<Map<String,Integer>> add(@Valid @RequestBody ShippingForm shippingForm, HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(), shippingForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVo delete(@PathVariable("shippingId") Integer shippingId,HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(shippingId,user.getId());
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVo update(@PathVariable("shippingId") Integer shippingId, HttpSession session,
                             @Valid @RequestBody ShippingForm shippingForm){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(shippingId,user.getId(), shippingForm);
    }
}
