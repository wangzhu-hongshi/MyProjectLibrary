package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategorySetviceImpl implements CategoryService {
    private CategoryDaoImpl cdi=new CategoryDaoImpl();

    /**
     * 查询分类
     * 如果第一次查询 就查询数据库
     * 否则查询redis
     * @return
     */
    @Override
    public List<Category> findAll() {
        //查询redis中是否有数据
        Jedis jedis = JedisUtil.getJedis();
        //使用排序查询
        //Set<String> categorys = jedis.zrange("Category", 0, -1);
        //把数据 和 分数都查询出来
        Set<Tuple> categorys = jedis.zrangeWithScores("Category", 0, -1);
        List<Category> cs=null;
        if(categorys==null||categorys.size()==0){
            System.out.println("从数据库查询。。。。");
            //redis中没有数据 查询mysql
            cs=cdi.findAll();
            //将查询出来的数据封装到redis中
            for (int i = 0; i <cs.size() ; i++) {
                jedis.zadd("Category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            cs=new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                System.out.println("从redis中查询。。。");
                //将set中的数据存储到 list中
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        return cs;
    }
    //旅游分类的分页查询
    @Override
    public PageBean<Route> pageQuery(int cid, int currpage, int pageSize, String rname) {
        //封装pageBean
        PageBean<Route> pageBean=new PageBean<>();
        //设置当前页码
        pageBean.setTurrentPage(currpage);
        //设置每页显示的数据条数
        pageBean.setPageSize(pageSize);
        //查询总记录
        int totaCount=cdi.findTotaCount(cid,rname);

        //设置总记录
        pageBean.setTotaCout(totaCount);
        //计算 索引
        int start=(currpage-1)*pageSize;
        List<Route> list=cdi.findByPage(cid,start,pageSize,rname);
        //设置list
        pageBean.setList(list);
        //设置最大页码
        int totaPage=totaCount%pageSize==0 ? totaCount/pageSize: totaCount/pageSize+1;
        pageBean.setTotaPage(totaPage);

        return pageBean;
    }
}
