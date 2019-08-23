package com.wang.lukymoney.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity//告知数据库对应的一张表
public class Luckymoney {
    @Id
    @GeneratedValue//自增长的注解
    private Integer id;

    @Min(value = 2,message = "最少要发2块钱")
    private BigDecimal money;

    //发送方
    private String producer;

    //接收方
    private String consumer;

    public Luckymoney() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }
}
