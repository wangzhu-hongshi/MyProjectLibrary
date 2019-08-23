package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql="select * from tab_category";
        List<Category> query = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return query;
    }
    /**
     * 查询总记录数 和 搜索模糊查询
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotaCount(int cid, String rname) {
        //定义sql模板
        String sql="select count(*) from tab_route where 1 = 1 ";
        StringBuilder builder=new StringBuilder(sql);
        List list =new ArrayList<>();
        if(cid!=0){
            //添加对应的值
            builder.append(" and cid=? ");
            list.add(cid);
        }
        if(rname!=null&&rname.length()>0){
            //添加对应的值
            builder.append(" and rname like ?");
            list.add("%"+rname+"%");
        }
        sql=builder.toString();
        return template.queryForObject(sql,Integer.class,list.toArray());
    }
    /**
     * 分类旅游的分页查询 和 搜索模糊查询
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//        String sql="select * from tab_route where cid=? limit ?,?";
        String sql="select * from tab_route where 1 = 1 ";
        StringBuilder builder=new StringBuilder(sql);
        List list =new ArrayList<>();
        if(cid!=0){
            //添加对应的值
            builder.append(" and cid=? ");
            list.add(cid);
        }
        if(rname!=null&&rname.length()>0){
            //添加对应的值
            builder.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        builder.append(" limit ?, ?");
        list.add(start);
        list.add(pageSize);
        sql=builder.toString();
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),list.toArray());

    }
}
