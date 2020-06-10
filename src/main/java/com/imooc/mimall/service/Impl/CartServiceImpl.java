package com.imooc.mimall.service.Impl;

import com.google.gson.Gson;
import com.imooc.mimall.dao.ProductMapper;
import com.imooc.mimall.enums.ProductStatusEnum;
import com.imooc.mimall.enums.ResponseEnum;
import com.imooc.mimall.form.CartAddForm;
import com.imooc.mimall.form.CartUpdateForm;
import com.imooc.mimall.pojo.Cart;
import com.imooc.mimall.pojo.Product;
import com.imooc.mimall.service.ICartService;
import com.imooc.mimall.vo.CartProductVo;
import com.imooc.mimall.vo.CartVo;
import com.imooc.mimall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author lhz
 * @version 1.0
 * @date 2020/6/4 0:15
 */
@Service
public class CartServiceImpl implements ICartService {
    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    private static final Integer quantity = 1;

    private Gson gson = new Gson();
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {

        //商品是否存在
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }


        //写进redis
        //不能把价格和库存等信息写进去，因为这些都是改变的,改变的只能用数据库，只需把productId，quantity，productSelected
        //key和value必须是String类型，需要把Cart对象转换成json
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        //因为每次添加进购物车都是加一，所以需要想读出来
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String hashKey = String.valueOf(product.getId());
        Cart cart;
        String value = opsForHash.get(redisKey, hashKey);
        if (StringUtils.isEmpty(value)) {
            //没有该商品就新增
            cart = new Cart(product.getId(), quantity, cartAddForm.getSelected());
        } else {
            //有了就加1
            cart = gson.fromJson(value, Cart.class);    //json转换为度先
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(redisKey,
                hashKey,
                gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);     //获取redis中的所有数据，get只能获取一条
        Set<Integer> productIdSet = new HashSet<>();
        Boolean selectedAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        if(entries.size()==0){
            cartVo.setCartTotalPrice(cartTotalPrice);
            cartVo.setCartTotalQuantity(cartTotalQuantity);
            cartVo.setSelectedAll(selectedAll);
            cartVo.setCartProductVoList(cartProductVoList);
            return ResponseVo.success(cartVo);
        }
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            productIdSet.add(productId);
        }
        List<Product> products = productMapper.selectByProductIdSet(productIdSet);
        if (products.size() > 0) {
            for (Product product : products) {
                String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
                Cart cart = gson.fromJson(value, Cart.class);
                CartProductVo cartProductVo = new CartProductVo(product.getId(),
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected());
                cartProductVoList.add(cartProductVo);
                if (!cart.getProductSelected()) {
                    selectedAll = false;
                }
                cartTotalQuantity += cart.getQuantity();
                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setSelectedAll(selectedAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String hashKey = String.valueOf(productId);
        String value = opsForHash.get(redisKey, hashKey);
        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        Cart cart = gson.fromJson(value, Cart.class);
        if (cartUpdateForm.getQuantity() != null && cartUpdateForm.getQuantity() >= 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if (cartUpdateForm.getSelected() != null) {
            cart.setProductSelected(cartUpdateForm.getSelected());
        }

        opsForHash.put(redisKey, hashKey, gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String hashKey = String.valueOf(productId);
        String value = opsForHash.get(redisKey, hashKey);
        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        opsForHash.delete(redisKey, hashKey);
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = 0;
        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            sum += cart.getQuantity();
        }

        //lambda+stream()
//        Integer sum = listForCart(uid).stream()
//                .map(Cart::getQuantity)
//                .reduce(0,Integer::sum);

        return ResponseVo.success(sum);
    }

    //遍历购物车
    public List<Cart> listForCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            String value = entry.getValue();
            cartList.add(gson.fromJson(value, Cart.class));
        }
        return cartList;
    }
}
