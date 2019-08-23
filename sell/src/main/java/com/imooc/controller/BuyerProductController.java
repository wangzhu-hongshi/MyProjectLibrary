package com.imooc.controller;

import com.imooc.VO.CategoryVO;
import com.imooc.VO.ProductInfoVO;
import com.imooc.VO.ResultVO;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.service.impl.CategoryServiceImpl;
import com.imooc.service.impl.InfoServiceImpl;
import com.imooc.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private InfoServiceImpl infoService;

    @GetMapping("/list")
    public ResultVO list(){
        //1.先查询 所有在架的商品
        List<ProductInfo> productInfoList = infoService.findUpAll();

        //2.再查询类目
        ArrayList<Integer> integers = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList) {
            integers.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(integers);
        //3.封装数据
        ArrayList<CategoryVO> categoryVOS = new ArrayList<>();

        for (ProductCategory productCategory : productCategories) {
            CategoryVO categoryVO = new CategoryVO();
            categoryVO.setCategoryName(productCategory.getCategoryName());
            categoryVO.setCategoryType(productCategory.getCategoryType());
            ArrayList<ProductInfoVO> productInfoVOS = new ArrayList<>();
            for (ProductInfo info : productInfoList) {
                if (info.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(info, productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            }
            categoryVO.setFoods(productInfoVOS);
            categoryVOS.add(categoryVO);
        }

//        ResultVO resultVO = new ResultVO();
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        resultVO.setData(categoryVOS);
//        System.out.println(resultVO);
        ResultVO sucess = ResultUtils.sucess(categoryVOS);
        return sucess;
    }
}
