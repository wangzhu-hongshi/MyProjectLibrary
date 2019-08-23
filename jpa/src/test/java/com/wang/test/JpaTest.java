package com.wang.test;

import com.wang.domain.Customer;
import com.wang.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {
    @Test
    public void testSave(){
        //1.加载配置文件创建工厂（实体管理器工厂对象）
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
//        //2.通过实体管理器工厂获取实体管理器
//        EntityManager em = factory.createEntityManager();
        //3.获取事务对象 开启事务
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //4.完成增删改查操作，保存一个各户道数据库中
        Customer customer=new Customer();
        customer.setCustName("王诸宏诗");
        customer.setCustIndustry("学生");
        em.persist(customer);
        //5.提交事务
        tx.commit();
        //6.释放资源
        em.close();
//        factory.close();
    }

    /**
     * find方法 是立即加载
     */
    @Test
    public void testfind(){
        //1.通过工具类获取实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.增删该查 根据id查询客户
        Customer customer = em.find(Customer.class, 1l);
        System.out.println(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * getReference方法
     * 1.获取对象是一个动态代理对象
     * 2.调用getReference方法不会立即发送sql查询数据库
     * 当调用查询结果对象的时候，才会发送查询的sql语句：什么时候调用什么时候发送sql语句查询数据库
     *
     * 延迟加载 或称为 懒加载
     *
     */
    @Test
    public void testReference(){
        //1.通过工具类获取实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.增删该查 根据id查询客户
        Customer customer = em.getReference(Customer.class, 1l);
        System.out.println(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 删除用户
     */
    @Test
    public void testRemove(){
        //1.通过工具类获取实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.增删该查 根据id查询客户
        Customer customer = em.find(Customer.class, 1l);
        //调用remove方法完成删除操作
        em.remove(customer);
//        System.out.println(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }

    /**
     * 更新用户
     */
    @Test
    public void testUpdate(){
        //1.通过工具类获取实体类管理器对象
        EntityManager em = JpaUtils.getEntityManager();
        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //3.增删该查 根据id查询客户
        Customer customer = em.find(Customer.class, 2l);
        //更新用户
        customer.setCustIndustry("大学生");
        em.merge(customer);
//        System.out.println(customer);
        //4.提交事务
        tx.commit();
        //5.释放资源
        em.close();
    }
}
