package com.wang.lukymoney.controller;

import com.wang.lukymoney.repository.LuckmoneyRepository;
import com.wang.lukymoney.domain.Luckymoney;
import com.wang.lukymoney.service.LuckymoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 控制器
 *
 */
@RestController
public class LuckymoneyController {
    @Autowired
    private LuckmoneyRepository repository;

    /**
     * 获取红包列表
     * @return
     */
    @GetMapping("/luckmoneys")
    public List<Luckymoney> list(){
                return repository.findAll();
    }

    /**
     * 创建红包（发红包）
     * @param
     * @param
     * @return
     */
    //@Valid 表单验证的注解
    @PostMapping("/luckmoneys")
    public Luckymoney create(@Valid Luckymoney luckymoney, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return null;
        }
        luckymoney.setProducer(luckymoney.getProducer());
        luckymoney.setMoney(luckymoney.getMoney());
        return repository.save(luckymoney);
    }
    /**
     *通过id查询红包
     */
    @GetMapping("/luckmoneys/{id}")
    public Luckymoney findById(@PathVariable("id") Integer id){
        return repository.findById(id).orElse(null);
    }

    /**
     * 更新 （领红包）
     * @return
     */
    @PostMapping("/luckmoneys/{id}")
    public Luckymoney update(@PathVariable("id") Integer id,
                             @RequestParam("consumer") String consumer){
        Optional<Luckymoney> byId = repository.findById(id);
        if(byId.isPresent()){
            Luckymoney luckymoney = byId.get();
            luckymoney.setConsumer(consumer);
            return repository.save(luckymoney);
        }
        return null;
    }
    @Autowired
    private LuckymoneyService service;
    @PostMapping("/luckmoneys/two")
    public void createTwo(){
        service.createTwo();
    }
}
