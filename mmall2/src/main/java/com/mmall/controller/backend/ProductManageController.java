package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 商品的后台管理
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IFileService iFileService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService productService;
    /**
     * 添加或者更新商品
     * @param product
     * @return
     */
    @RequestMapping(value = "save.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest request, Product product){
        //判断是否登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            return productService.saveOrUpdateProduct(product);
//        }else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
        //使用拦截器判断是否登陆
        return productService.saveOrUpdateProduct(product);
    }

    /**
     * 修改商品状态
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "set_sale_status.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId, Integer status){
        //判断是否登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            //修改商品状态
//            return productService.setSaleStatus(productId,status);
//        }else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
        return productService.setSaleStatus(productId,status);
    }

    /**
     * 产品详情
     * @param
     * @param productId
     * @return
     */
    @RequestMapping(value = "detail.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId){
        //判断是否登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//
//            return productService.manageProductDetail(productId);
//
//        }else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
        return productService.manageProductDetail(productId);
    }

    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //判断是否登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//
//            return productService.getProductList(pageNum,pageSize);
//
//        }else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
        return productService.getProductList(pageNum,pageSize);
    }

    /**
     * 产品搜索
     * @param
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "search.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest request,String productName,Integer productId,
                                        @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        //判断是否登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            return productService.searchProduct(productName,productId,pageNum,pageSize);
//        }else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
        return productService.searchProduct(productName,productId,pageNum,pageSize);
    }

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(
                                 @RequestParam(value = "upload_file" ,required = false) MultipartFile file,
                                 HttpServletRequest request){

//        //判断是否登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            return  ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登陆,无法获取当前用户的信息,status=10,强制登陆");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//
//        }else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
        String targetFileName = iFileService.upload(file, "product");
        if (targetFileName == null) {
            return ServerResponse.createByErrorMessage("文件上传失败");
        }
        String url= PropertiesUtil.getProperty("qiniu.resource.server.http.prefix")+targetFileName;
        Map fileMap= Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }

    /**
     * 富文本上传图片
     * @param session
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file" ,required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        Map resultMap=Maps.newHashMap();
        //判断是否登陆
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(loginToken==null){
//            resultMap.put("success",false);
//            resultMap.put("msg","请登录管理员");
//            return resultMap;
//        }
//        User user = JsonUtil.string2Obj(RedisShardedPoolUtil.get(loginToken), User.class);
//        if(user==null){
//            resultMap.put("success",false);
//            resultMap.put("msg","请登录管理员");
//            return resultMap;
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            //路径
//
//        }else {
//            resultMap.put("success",false);
//            resultMap.put("msg","无访问权限");
//            return resultMap;
//        }
        String path=request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        if(StringUtils.isBlank(targetFileName)){
            resultMap.put("success",false);
            resultMap.put("msg","上传失败");
            return resultMap;
        }
        String url= PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
        resultMap.put("success",true);
        resultMap.put("msg","上传成功");
        resultMap.put("file_path",url);
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }

}
