package com.wang.test;

import com.wang.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class JqplTest {
    /**
     * 面向对象的查询
     * 查询全部
     *      jqpl:form 实体类对象名
     *      sql:select * form 表名
     */
    @Test
    public void testfindAll(){
        //获取 实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //查询全部
        String jqpl="from Customer";
        Query query = em.createQuery(jqpl);
        //发送查询，并封装结果集
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }
        //提交事务
        tx.commit();
        //关闭资源
        em.close();
    }

    /**
     * 倒序
     */
    @Test
    public void testOrders(){
        //获取 实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //查询全部
        String jqpl="from Customer order by custId desc";
        Query query = em.createQuery(jqpl);
        //发送查询，并封装结果集
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }
        //提交事务
        tx.commit();
        //关闭资源
        em.close();
    }

    /**
     * 统计
     */
    @Test
    public void testCount(){
        //获取 实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //查询全部
        String jqpl="select count(custId)from Customer ";
        Query query = em.createQuery(jqpl);
        //发送查询，并封装结果集
        Object singleResult = query.getSingleResult();
        System.out.println(singleResult);
        //提交事务
        tx.commit();
        //关闭资源
        em.close();
    }
    @Test
    public void testPaged (){
        //获取 实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //查询全部
        String jqpl="from Customer";
        Query query = em.createQuery(jqpl);
        //对参数进行赋值
        query.setFirstResult(0);
        query.setMaxResults(2);
        //发送查询，并封装结果集
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }
        //提交事务
        tx.commit();
        //关闭资源
        em.close();
    }
    @Test
    public void testCondition(){
        //获取 实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //查询全部
        String jqpl="from Customer where custName like ?";
        Query query = em.createQuery(jqpl);
        //对参数进行赋值
        query.setParameter(1,"王%");
        //发送查询，并封装结果集
        List list = query.getResultList();
        for (Object o : list) {
            System.out.println(o);
        }
        //提交事务
        tx.commit();
        //关闭资源
        em.close();
    }
}
