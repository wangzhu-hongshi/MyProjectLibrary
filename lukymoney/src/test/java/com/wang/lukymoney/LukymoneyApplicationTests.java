package com.wang.lukymoney;

import com.wang.lukymoney.domain.Luckymoney;
import com.wang.lukymoney.repository.LuckmoneyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LukymoneyApplicationTests {

    @Autowired
    private LuckmoneyRepository repository;

    @Test
    public void testFindOne() {
        Luckymoney luckymoney = new Luckymoney();
        luckymoney.setId(1);
        Example<Luckymoney> of = Example.of(luckymoney);
        luckymoney = repository.findOne(of).get();
        System.out.println(luckymoney);
    }



    @Test
    public void contextLoads() {
    }

}
