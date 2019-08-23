package com.wang.test;

import com.wang.dao.IAccountDao;
import com.wang.dao.IUserDao;
import com.wang.domain.Account;
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
 * User的测试类
 */
public class AccountTest {
    private InputStream in;
    private SqlSessionFactory factory;
    private SqlSession sqlSession;
    private IAccountDao iAccountDao;
    /**
     * 测试方法开始前执行
     */
    @Before
    public void init() throws IOException {
        in = Resources.getResourceAsStream("SqlMapconfig.xml");
        factory = new SqlSessionFactoryBuilder().build(in);
        sqlSession = factory.openSession();
        iAccountDao = sqlSession.getMapper(IAccountDao.class);
    }
    @After
    public void destroy() throws IOException {
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }
    @Test
    public void findAll(){
        List<Account> all = iAccountDao.findAll();
        for (Account account : all) {
            System.out.println(account.getUser());
            System.out.println(account);
        }
    }

}
