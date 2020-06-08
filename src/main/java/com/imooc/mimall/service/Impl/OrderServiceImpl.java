package com.imooc.mimall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mimall.dao.OrderItemMapper;
import com.imooc.mimall.dao.OrderMapper;
import com.imooc.mimall.dao.ProductMapper;
import com.imooc.mimall.dao.ShippingMapper;
import com.imooc.mimall.enums.OrderStatusEnum;
import com.imooc.mimall.enums.PaymentTypeEnum;
import com.imooc.mimall.enums.ProductStatusEnum;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.pojo.*;
import com.imooc.mimall.service.ICartService;
import com.imooc.mimall.service.IOrderService;
import com.imooc.mimall.vo.OrderItemVo;
import com.imooc.mimall.vo.OrderVo;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/7 19:09
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    @Transactional      //开启事务，order和orderItem同时写入数据库，如果哪张表出错则都不写入,出现RuntimeException才会回滚
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //收获地址校验（总之要查出来）
        Shipping shipping = shippingMapper.selectByIdAndUid(shippingId, uid);
        if (shipping == null) {
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        //获取购物车，校验（看是否有商品，库存）
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        //计算总价，只计算选中的商品
        //想要查出选中购物车的所有商品信息
        Set<Integer> productIdSet = cartList.stream().map(Cart::getProductId)
                .collect(Collectors.toSet());
        List<Product> products = productMapper.selectByProductIdSet(productIdSet);
        //把商品转换成一个map，这样就可以不经数据库查到数据
        Map<Integer, Product> map = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        //生成订单号
        Long orderNo = generateOrderNo();

        List<OrderItem> orderItemList = new ArrayList<>();
        for (Cart cart : cartList) {
            //根据productId查数据库
            Product product = map.get(cart.getProductId());
            if (product == null) {
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "商品不存在。 productId = " + cart.getProductId());
            }
            //库存是否充足
            if (cart.getQuantity() > product.getStock()) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,
                        "库存不足" + product.getName());
            }
            //商品是否是在售状态
            if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,
                        "商品不是在售状态");
            }

            //构建orderItem
            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);

            //减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }

            //更新购物车（删除选中的商品），不能写在循环里面，redis没有回滚功能
        }

        //生成订单
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        //写入order，orderItem表，要一起写入，事务
        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        //更新购物车
        //redis有事务（打包），但是无回滚
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }

        //构造OrderVo
        order = orderMapper.selectByOrderNo(orderNo);       //查询数据库，获取数据库里自动生成的字段
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);
        List<OrderVo> orderVoList = new ArrayList<>();
        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        //list转map，这里因为value是一个数组，所以要用到groupingBy
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId, shipping -> shipping));

        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order, orderItemMap.get(order.getOrderNo()), shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ResponseVo.success(pageInfo);
    }

    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //查询出来的OrderItem全部转换未OrderItemVo
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(order.getOrderNo());

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order==null||!order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //这里只设置未付款才能取消，我们可以根据公司具体业务来设置
        if(!order.getStatus().equals(OrderStatusEnum.NOT_PAY.getCode())){
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }
        order.setStatus(OrderStatusEnum.CANCEL.getCode());


        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if(row<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.successByMsg("订单取消成功");
    }

    private OrderItem buildOrderItem(Integer uid, Long OrderNo, Integer quantity, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setUserId(uid);
        orderItem.setOrderNo(OrderNo);
        orderItem.setQuantity(quantity);
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setProductId(product.getId());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setProductName(product.getName());
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }

    private Order buildOrder(Integer uid, Long OrderNo, Integer shippingId, List<OrderItem> orderItemList) {
        Order order = new Order();
        BigDecimal payment = orderItemList.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setUserId(uid);
        order.setOrderNo(OrderNo);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setShippingId(shippingId);
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        return order;
    }

    /**
     * 这里只是做测试生成的订单号
     * 如果是企业级的项目就要用到分布式生成唯一id
     *
     * @return
     */
    private Long generateOrderNo() {
        //时间戳加上三位数的随机数
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        List<OrderItemVo> OrderItemVoList = orderItemList.stream()
                .map(e -> {
                    OrderItemVo orderItemVo = new OrderItemVo();
                    BeanUtils.copyProperties(e, orderItemVo);
                    return orderItemVo;
                }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(OrderItemVoList);
        if(shipping!=null){
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }
        return orderVo;
    }
}
