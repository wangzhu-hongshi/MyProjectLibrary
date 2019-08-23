package com.imooc.controller;

import com.imooc.Vo.ProductCategoryVO;
import com.imooc.Vo.ProductInfoVO;
import com.imooc.Vo.ResultVO;
import com.imooc.dataobjct.ProductCategory;
import com.imooc.dataobjct.ProductInfo;
import com.imooc.service.impl.CategoryServiceImpl;
import com.imooc.service.impl.ProductInfoServiceImpl;
import com.imooc.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductInfoServiceImpl infoService;
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/list")
//    @Cacheable(cacheNames = "product",key = "123")

    public ResultVO list(){
        //1.查询所有在架的商品
        List<ProductInfo> infos = infoService.findUpA();
        //2.根据类目编号查询所有类目
        List<Integer> types=new ArrayList<>();
        for (ProductInfo info : infos) {
            types.add(info.getCategoryType());
        }
        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(types);
        //封装数据
        List<ProductCategoryVO> categoryVOList=new ArrayList<>();
        //System.out.println(categories);
        for (ProductCategory category : categories) {
            ProductCategoryVO categoryVO=new ProductCategoryVO();
            categoryVO.setCategoryName(category.getCategoryName());
            categoryVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> infoVOList=new ArrayList<>();
            for (ProductInfo info : infos) {
                if(info.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVO infoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(info,infoVO);
                    infoVOList.add(infoVO);
                }
            }
            categoryVO.setProductInfoVOList(infoVOList);
            //System.out.println(categoryVO);
            categoryVOList.add(categoryVO);
        }
        ResultVO resultVO = ResultVOUtil.success(categoryVOList);
        return resultVO;
    }
}
