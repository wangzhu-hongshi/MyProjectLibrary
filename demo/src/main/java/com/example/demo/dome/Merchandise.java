package com.example.demo.dome;

public class Merchandise implements Merchandise1 {
    private int m=1000;
    @Override
    public void a() {
        System.out.println("这个产品买"+m+"钱");
    }

    @Override
    public int bbb() {
        return m;
    }
}
