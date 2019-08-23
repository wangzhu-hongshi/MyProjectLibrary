package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.RedisPoolUtil;
import com.mmall.utils.RedisShardedPoolUtil;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 购物车的门户类
 */
@Controller
@RequestMapping("/cart/")
public class CartController {
    @Autowired
    private ICartService iCartService;

    /**
     * 添加到购物车功能
     * @param
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse<CartVo> add(HttpServletRequest request, Integer productId, Integer count){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iCartService.add(productId,user.getId(),count);
    }

    /**
     * 更新购物车
     * @param
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse<CartVo> update(HttpServletRequest request, Integer productId, Integer count){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
       return iCartService.update(productId,user.getId(),count);
    }

    /**
     * 删除购物车中的产品
     * @param
     * @param productIds
     * @return
     */
    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse<CartVo> deleteProduct(HttpServletRequest request, String productIds){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iCartService.deleteProduct(user.getId(),productIds);
    }

    /**
     * 查询所有的购物车产品
     * @param
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iCartService.list(user.getId());
    }

    /**
     * 全选
     * @param
     * @return
     */
    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> selectAll(HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iCartService.selectOrUnSelect(user.getId(),Const.Cart.CHECKED,null);
    }

    /**
     * 全反选
     * @param
     * @return
     */
    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iCartService.selectOrUnSelect(user.getId(),Const.Cart.UN_CHECKED,null);
    }

    /**
     * 单选
     * @param
     * @param productId
     * @return
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<CartVo> select(HttpServletRequest request,Integer productId){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iCartService.selectOrUnSelect(user.getId(),Const.Cart.CHECKED,productId);
    }

    /**
     * 反单选
     * @param
     * @param productId
     * @return
     */
    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelect(HttpServletRequest request,Integer productId){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iCartService.selectOrUnSelect(user.getId(),Const.Cart.UN_CHECKED,productId);
    }

    /**
     * 购物车中产品的总数
     * @param
     * @return
     */
    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        if(loginToken==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
        if(user==null){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
        }
        return iCartService.getCartProductCount(user.getId());
    }




}
