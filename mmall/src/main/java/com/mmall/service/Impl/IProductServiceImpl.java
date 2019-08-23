package com.mmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.utils.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品业务层实现类
 */
@Service("iProductService")
public class IProductServiceImpl implements IProductService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加产品或者更新产品
     * @param product
     * @return
     */
    public ServerResponse saveOrUpdateProduct(Product product){
        //1.判断参数是否为空
        if(product!=null){
            //2.把子图设置为主图
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            //3.以id是否为空进行判断 更新还新增
            if(product.getId()!=null){
                //不为空 进行更新
                int rowCount=productMapper.updateByPrimaryKey(product);
                if(rowCount>0){
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }
                return ServerResponse.createByErrorMessage("更新产品失败");
            }else {
                int rowCount=productMapper.insert(product);
                if(rowCount>0){
                    return ServerResponse.createBySuccessMessage("新增产品成功");
                }
                return ServerResponse.createByErrorMessage("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增或更新的参数不正确");
    }

    /**
     * 修改商品状态码
     * @param productId
     * @param status
     * @return
     */
    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        if(productId==null||status==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product=new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount>0){
            return ServerResponse.createBySuccessMessage("修改商品状态成功");
        }
        return ServerResponse.createByErrorMessage("修改商品状态失败");
    }

    /**
     * 产品详情
     * @param productId
     * @return
     */
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId==null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByErrorMessage("产品已下架或者已删除");
        }
        ProductDetailVo productDetailVo=assemblePorductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }
    public ProductDetailVo assemblePorductDetailVo(Product product){
        ProductDetailVo productDetailVo=new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.mmall.com/"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category==null){
            productDetailVo.setParentCategoryId(0);
        }else
            productDetailVo.setParentCategoryId(category.getParentId());

        return productDetailVo;
    }

    /**
     * 产品list（分页）
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getProductList(int pageNum,int pageSize){
        //start Page--start
        //填充自己的sql查询逻辑
        //pageHelper-收尾
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> listVoList= Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assembkeProductListVo(product);
            listVoList.add(productListVo);
        }
        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(listVoList);
        return ServerResponse.createBySuccess(pageResult);



    }

    private ProductListVo assembkeProductListVo(Product product){
        ProductListVo productListVo=new ProductListVo();
        BeanUtils.copyProperties(product,productListVo);
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://image.mmall.com/"));
        return productListVo;
    }

    /**
     * 搜索产品
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName)){
            productName=new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId( productName,productId);
        List<ProductListVo> listVoList= Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assembkeProductListVo(product);
            listVoList.add(productListVo);
        }
        PageInfo pageResult=new PageInfo(productList);
        pageResult.setList(listVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     * 前台的productDetail
     * @param productId
     * @return
     */
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId){
        if(productId==null){
            return ServerResponse.createByErrorMessage("参数错误！");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null){
            return ServerResponse.createByErrorMessage("产品不存在");
        }
        if(product.getStatus()!= Const.ProductStatusEnum.ON_SALE.getCode()){
            return ServerResponse.createByErrorMessage("产品已下架或者已删除");
        }
        ProductDetailVo productDetailVo = assemblePorductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    /**
     * 前台产品搜索及动态排序
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,
                                                                int pageNum,int pageSize,String orderBy){
        if(StringUtils.isBlank(keyword)&& categoryId==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList=new ArrayList<>();
        if(categoryId!=null){
            //查询分类
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category==null&& StringUtils.isBlank(keyword)){
                //没有该分类 ，并且还没有关键字，这个时候返回一个空的结果集,不报错
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVo> productListVoList= Lists.newArrayList();
                PageInfo pageInfo=new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            //查询 子类的类目
            categoryIdList = iCategoryService.selectCategoryAndChidrenById(categoryId).getData();
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword=new StringBuffer().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum,pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy)){
            if(Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
                String[] orderByArray= orderBy.split("_");
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, categoryIdList.size() == 0 ? null : categoryIdList);
        List<ProductListVo> productListVoList=Lists.newArrayList();
        for (Product product : productList) {
            ProductListVo productListVo = assembkeProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo=new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
