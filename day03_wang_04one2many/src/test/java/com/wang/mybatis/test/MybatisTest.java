package com.wang.mybatis.test;

import com.wang.Dao.IUserDao;
import com.wang.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 */
public class MybatisTest {
    private InputStream in;
    private SqlSession sqlSession;
    private IUserDao iUserDao;
    @Before//用于在则是方法之前执行
    public void init() throws IOException {
         in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建 SqlSessionFactory 的构建者对象
        //3.使用构建者创建工厂对象 SqlSessionFactory
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(in);
        //4.使用 SqlSessionFactory 生产 SqlSession 对象
         sqlSession = build.openSession();
        //5.使用 SqlSession 创建 dao 接口的代理对象
         iUserDao = sqlSession.getMapper(IUserDao.class);

    }
    @After//在测试方法之后执行
    public void destroy() throws IOException {
        //提交事务
        sqlSession.commit();
        //7.释放资源
        sqlSession.close();
        in.close();
    }
    /**
     * 查询User
     * @throws Exception
     */
    @Test
   public void testFindAll() throws  Exception{
       //1.读取配置文件

       //6.使用代理对象执行查询所有方法
       List<User> list = iUserDao.findAll();
       for (User user : list) {
           System.out.println(user);

       }

   }


}
