package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

/**
 * 前台产品接口
 */
@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    /**
     * 产品detail
     * @param
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){

        return iProductService.getProductDetail(productId);
    }
    @RequestMapping(value = "/{productId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detailRESTful(@PathVariable Integer productId){

        return iProductService.getProductDetail(productId);
    }

    /**
     * 产品搜索及动态排序
     * @param keyword  关键字
     * @param categoryId 类目名字
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword",required = false) String keyword,
                                         @RequestParam(value = "categoryId",required = false)Integer categoryId,
                                         @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "")String orderBy){
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    /**
     * RESTful
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping(value = "/{keyword}/{categoryId}/{pageNum}/{pageSize}/{orderBy}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful(@PathVariable(value = "keyword") String keyword,
                                         @PathVariable(value = "categoryId")Integer categoryId,
                                         @PathVariable(value = "pageNum")Integer pageNum,
                                         @PathVariable(value = "pageSize")Integer pageSize,
                                         @PathVariable(value = "orderBy")String orderBy){
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        if(orderBy==null){
            orderBy="price_asc";
        }

        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    @RequestMapping(value = "/categoryId/{categoryId}/{pageNum}/{pageSize}/{orderBy}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful(
                                                @PathVariable(value = "categoryId")Integer categoryId,
                                                @PathVariable(value = "pageNum")Integer pageNum,
                                                @PathVariable(value = "pageSize")Integer pageSize,
                                                @PathVariable(value = "orderBy")String orderBy){
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        if(orderBy==null){
            orderBy="price_asc";
        }


        return iProductService.getProductByKeywordCategory(" ",categoryId,pageNum,pageSize,orderBy);
    }

    @RequestMapping(value = "/keyword/{keyword}/{pageNum}/{pageSize}/{orderBy}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful(@PathVariable(value = "keyword") String keyword,
                                                @PathVariable(value = "pageNum")Integer pageNum,
                                                @PathVariable(value = "pageSize")Integer pageSize,
                                                @PathVariable(value = "orderBy")String orderBy){
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        if(orderBy==null){
            orderBy="price_asc";
        }

        return iProductService.getProductByKeywordCategory(keyword,null,pageNum,pageSize,orderBy);
    }


}
