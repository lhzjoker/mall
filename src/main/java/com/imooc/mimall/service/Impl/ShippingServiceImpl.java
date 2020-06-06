package com.imooc.mimall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mimall.dao.ShippingMapper;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.form.ShippingForm;
import com.imooc.mimall.pojo.Shipping;
import com.imooc.mimall.service.IShippingService;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/5 22:15
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.ERROR, "新建地址失败");
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("shippingId", shipping.getId());
        return ResponseVo.success(map, "新建地址成功");
    }

    @Override
    public ResponseVo delete(Integer shippingId, Integer uid) {
        int row = shippingMapper.deleteByIdAndUid(shippingId, uid);
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.successByMsg("删除地址成功");
    }

    @Override
    public ResponseVo update(Integer shippingId, Integer uid, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, shipping);
        shipping.setId(shippingId);
        shipping.setUserId(uid);
        int row = shippingMapper.updateByIdAndUidSelective(shipping);
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.ERROR, "更新地址失败");
        }
        return ResponseVo.successByMsg("更新地址成功");
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippings = shippingMapper.selectByUserId(uid);
        PageInfo pageInfo = new PageInfo(shippings);
        return ResponseVo.success(pageInfo);
    }
}
