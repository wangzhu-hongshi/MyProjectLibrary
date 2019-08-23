package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();

    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    int findTotaCount(int cid, String rname);

    /**
     * 分类旅游的分页查询
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    List<Route> findByPage(int cid, int start, int pageSize, String rname);
}
