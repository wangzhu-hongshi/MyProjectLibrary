package com.mmall.service.Impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.utils.BigDecimalUtil;
import com.mmall.utils.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("iCartService")
public class ICartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加一个商品进购物车
     * @param productId
     * @param userId
     * @param count
     * @return
     */
    public ServerResponse<CartVo> add(Integer productId,Integer userId,Integer count){
        if(productId==null||count==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        //查询购物车里的产品
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart==null){
            //这个产品不在这个购物车里,就把该产品添加到购物车了
            Cart cartItem =new Cart();
            cartItem.setChecked(Const.Cart.CHECKED);//选中状态
            cartItem.setQuantity(count);//数量
            cartItem.setProductId(productId);//产品id
            cartItem.setUserId(userId);//用户id
            cartMapper.insert(cartItem);//保存进数据库
        }else {
            //如果产品已经存在购物车了 添加数量即可
            cart.setQuantity(cart.getQuantity()+count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        //返回的是整个购物的视图对象
        return this.list(userId);
    }

    /**
     * 更新购物车
     * @param productId
     * @param userId
     * @param count
     * @return
     */
    public ServerResponse<CartVo> update(Integer productId,Integer userId,Integer count){
        if(productId==null||count==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(cart!=null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return this.list(userId);
    }

    /**
     * 删除购物车中的产品
     * @param userId
     * @param productIds
     * @return
     */
    public ServerResponse<CartVo> deleteProduct(Integer userId,String productIds){
        List<String> productList= Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId,productList);
        return this.list(userId);
    }

    /**
     * 查询购物车的所有产品
     * @param userId
     * @return
     */
    public ServerResponse<CartVo> list(Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    /**
     * 全选 或者 全反选 单选 单反选
     * @param userId
     * @param checked
     * @return
     */
    public ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer checked,Integer productId){
        cartMapper.checkedOrUncheckedProduct(userId,checked,productId);
        return this.list(userId);
    }

    /**
     * 购物车里的产品的总数
     * @param userId
     * @return
     */
    public ServerResponse<Integer> getCartProductCount(Integer userId){
        if(userId==null){
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }

    /**核心方法
     * 整个购物车的视图对象
     * @param userId
     * @return
     */
    public CartVo getCartVoLimit(Integer userId){
        //1.所有产品的购物车视图对象
        CartVo cartVo=new CartVo();
        //查询该用户下购物车的所有对象
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList=Lists.newArrayList();
        //计算总价的
        BigDecimal cartTotaPrice= new BigDecimal("0");
        //当购物车不是空的
        if(CollectionUtils.isNotEmpty(cartList)){
            //2.遍历购物车 组建CartProductVo
            for (Cart cart : cartList) {
                //单个产品的购物车视图
                CartProductVo cartProductVo=new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(cart.getUserId());
                cartProductVo.setProductId(cart.getProductId());
                //查询产品 赋值给视图
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if(product!=null){
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //3.todo 判断库存
                    int buyLimitCount=0;
                    //数据库里的商品库存大于用户购买的数量
                    if(product.getStock()>=cart.getQuantity()){
                        //库存充足的时候
                        buyLimitCount=cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        //库存不够 最多只能购买库存里的数量
                        buyLimitCount=product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //更新有效数据库
                        Cart cartForQuantity=new Cart();
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartForQuantity.setId(cart.getId());
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    //实际能购买的数量
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算该商品的总价（单价乘以数量）
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    //设值勾选
                    cartProductVo.setProductChecked(cart.getChecked());
                    //判断是否已经勾选上
                    if(cart.getChecked()==Const.Cart.CHECKED){
                        //勾选 加上整个购物车的总价
                        cartTotaPrice=BigDecimalUtil.add(cartTotaPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                    }
                    cartProductVoList.add(cartProductVo);
                }
            }
        }
        //组建cartVo
        cartVo.setCartTotalPrice(cartTotaPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVo;
    }
    //判断是否是全选状态
    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null)
            return false;
        return cartMapper.selectCartProductCheckedStatusByUserId(userId)==0;
    }
}
