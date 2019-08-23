package com.wang.test;

import com.wang.dao.IAccountDao;
import com.wang.domain.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMybatis {
    /**
     * 查询所有
     * @throws Exception
     */
    @Test
    public void Test1() throws Exception {
        //获取配置文件的字节流
        InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = build.openSession();
        IAccountDao iAccountDao = sqlSession.getMapper(IAccountDao.class);
        List<Account> all = iAccountDao.findAll();
        for (Account account : all) {
            System.out.println(account);
        }
    }

    /**
     * 保存账户
     * @throws Exception
     */
    @Test
    public void Test2() throws Exception {
        Account account=new Account();
        account.setName("宏宏");
        account.setMoney(3000);
        //获取配置文件的字节流
        InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = build.openSession();
        IAccountDao iAccountDao = sqlSession.getMapper(IAccountDao.class);
        iAccountDao.saveAccount(account);
        //增删改需要提交事务
        sqlSession.commit();
        sqlSession.close();
        is.close();

    }
}
