package com.wang.test;

import com.wang.dao.IUserDao;
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

public class MybatisTset {
    InputStream in;
    SqlSessionFactory factory;
    SqlSession sqlSession;
    IUserDao mapper;
    @Before
    public void init () throws IOException {
         in = Resources.getResourceAsStream("ISqlCongig.xml");
         factory = new SqlSessionFactoryBuilder().build(in);
         sqlSession = factory.openSession();
         mapper = sqlSession.getMapper(IUserDao.class);
    }
    @After
    public void destroy() throws IOException {
        sqlSession.close();
        sqlSession.commit();
        in.close();
    }
    @Test
    public void test01() throws IOException {

        List<User> all = mapper.findAll();
        for (User user : all) {
            System.out.println(user);
        }

        sqlSession.close();
        in.close();
    }
    @Test
    public void test02() throws IOException {
        User byId = mapper.findById(41);
        System.out.println(byId);
    }

}
