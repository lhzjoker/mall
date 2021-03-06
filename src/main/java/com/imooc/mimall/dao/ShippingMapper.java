package com.imooc.mimall.dao;

import com.imooc.mimall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    List<Shipping> selectByUserId(Integer userId);

    int deleteByIdAndUid(@Param("shippingId") Integer shippingId,@Param("userId") Integer userId);

    int updateByIdAndUidSelective(Shipping record);

    Shipping selectByIdAndUid(@Param("shippingId") Integer shippingId,@Param("userId") Integer userId);

    List<Shipping> selectByIdSet(@Param("idSet") Set<Integer> idSet);

}