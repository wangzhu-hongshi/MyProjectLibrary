package com.example.demo.dome;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        Merchandise merchandise=new Merchandise();


        Merchandise1 bbb =(Merchandise1) Proxy.newProxyInstance(merchandise.getClass().getClassLoader(), merchandise.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String name = method.getName();
                if (name.equals("bbb")) {
                    int invoke = (int) method.invoke(merchandise);
                    return invoke * 2;
                } else {
                    return method.invoke(merchandise);
                }

            }
        });

        int bbb1 = bbb.bbb();
        System.out.println(bbb1);
    }
}
