package com.mmall.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * 类目接口实现类
 *
 */
@Slf4j
@Service("iCategoryService")
public class ICategoryServiceImpl implements ICategoryService{

//    private Logger logger= LoggerFactory.getLogger(ICategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加类目
     * @param categoryName
     * @param parentId
     * @return
     */
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if(parentId==null||StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category=new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if (rowCount>0){
            return ServerResponse.createBySuccessMessage("类目添加成功");
        }
        return ServerResponse.createByErrorMessage("添加类目失败");
    }

    /**
     * 修改类目
     * @param categoryId
     * @param categoryName
     * @return
     */
    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if(categoryId==null||StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category=new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount>0){
            return ServerResponse.createBySuccessMessage("更新类目名字成功");
        }
        return ServerResponse.createByErrorMessage("更新类目名字失败");
    }

    /**
     * 查询子分类
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        if(categoryId==null){
            return ServerResponse.createByErrorMessage("parentId 参数错误");
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);

    }

    /**
     * 递归查询本节点的id及孩子节点id
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChidrenById(Integer categoryId){
        Set<Category> categorySet= Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<Integer> categoryIdList= Lists.newArrayList();
        if(categoryId!=null){
            for (Category category : categorySet) {
                categoryIdList.add(category.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);

    }

    //递归算法，算出子节点
    public Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        //判断当前类目是否存在
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null){
            categorySet.add(category);
        }
        //查找子节点，递归算法一定有一个退出条件
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList) {
                findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
