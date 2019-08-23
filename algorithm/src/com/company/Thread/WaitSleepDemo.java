package com.company.Thread;

public class WaitSleepDemo {
    public static void main(String[] args) {
        final Object look=new Object();
        System.out.println("开始执行");
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("A线程获取到cup");
                synchronized(look){
                    try {
                        System.out.println("A线程获取到锁的");
                        System.out.println("A睡个觉");
                        Thread.sleep(20);
                        System.out.println("A睡个大觉");
                        look.wait(500);
                        System.out.println("A又醒了");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        try{
            Thread.sleep(10);
        }catch (Exception e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("B线程获取到cup");
                synchronized(look){
                    try {
                        System.out.println("B线程获取到锁的");
                        System.out.println("B睡个叫");
                        Thread.sleep(20);
                        System.out.println("B醒了");

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
