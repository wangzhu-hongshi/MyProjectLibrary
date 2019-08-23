package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    PageBean<Route> pageQuery(int cid, int currpage, int pageSize, String rname);
}
