package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.utils.CookieUtil;
import com.mmall.utils.JsonUtil;
import com.mmall.utils.RedisPoolUtil;
import com.mmall.utils.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 后台的类目管理
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryMangeController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;
    /**
     * 添加类目节点
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryName, @RequestParam(value ="parentId" ,defaultValue = "0") int parentId){
//        //1.判断登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        //2.校验是否是管理员
//        ServerResponse response = iUserService.checkAdminRole(user);
//        if(!response.isSuccess()){
//            return ServerResponse.createByErrorMessage("该用户不是管理员,没有权限");
//        }
        //添加类目
        return iCategoryService.addCategory(categoryName,parentId);

    }

    /**
     * 更新类目名
     * @param
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping(value = "set_category.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse setCategory(HttpServletRequest request,Integer categoryId,String categoryName){
        //1.判断登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        //2.校验是否是管理员
//        ServerResponse response = iUserService.checkAdminRole(user);
//        if(!response.isSuccess()){
//            return ServerResponse.createByErrorMessage("该用户不是管理员，没权限");
//        }
        //修改类目
        return iCategoryService.updateCategoryName(categoryId,categoryName);
    }

    /**查询子节点的category信息，并且不递归， 保持平级
     *
     * @param
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        //1.判断登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        //2.校验是否是管理员
//        ServerResponse response = iUserService.checkAdminRole(user);
//        if(!response.isSuccess()){
//            return ServerResponse.createByErrorMessage("该用户不是管理员没权限");
//        }
        //查询子节点的category信息，并且不递归， 保持平级
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 查询当前节点的id和递归节点的id
     * @param
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_deep_category.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest request,@RequestParam(value = "categoryId",defaultValue = "0")Integer categoryId){
        //1.判断登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        //2.校验是否是管理员
//        ServerResponse response = iUserService.checkAdminRole(user);
//        if(!response.isSuccess()){
//            return ServerResponse.createByErrorMessage("该用户不是管理员没权限.");
//        }
        //查询当前节点的id和递归节点的id
        return iCategoryService.selectCategoryAndChidrenById(categoryId);
    }
}
