package com.wang.test;

import com.wang.dao.IUserDao;
import com.wang.domain.User;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * User的测试类
 */
public class UserTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IUserDao iUserDao;
    /**
     * 测试方法开始前执行
     */
    @Before
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapconfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        iUserDao = sqlSession.getMapper(IUserDao.class);
    }
    @After
    public void destroy() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }
    @Test
    public void findAll(){
        List<User> all = iUserDao.findAll();
        for (User user : all) {
            System.out.println(user);
        }
    }

}
