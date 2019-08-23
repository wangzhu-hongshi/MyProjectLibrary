package com.wang.lukymoney.service;

import com.wang.lukymoney.domain.Luckymoney;
import com.wang.lukymoney.repository.LuckmoneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service("luckymoneyService")
public class LuckymoneyService {

    @Autowired
    private LuckmoneyRepository repository;
    /**
     * 连续发两个红包 保持事务的一致性
     */
    @Transactional
    public void createTwo(){
        Luckymoney luckymoney=new Luckymoney();
        luckymoney.setProducer("陆建");
        luckymoney.setMoney(new BigDecimal("520"));
        repository.save(luckymoney);

        Luckymoney luckymoney2=new Luckymoney();
        luckymoney2.setProducer("陆建");
        luckymoney2.setMoney(new BigDecimal("1314"));
        repository.save(luckymoney2);
    }

    public void getAge(Integer id){
        Optional<Luckymoney> luckymoney = repository.findById(id);
        Luckymoney luckymoney1 = luckymoney.get();
        System.out.println(luckymoney1);
    }

}
