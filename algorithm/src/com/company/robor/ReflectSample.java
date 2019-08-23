package com.company.robor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectSample {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        Class rc=Class.forName("com.company.robor.Robor");
        Robor o =(Robor) rc.newInstance();
        System.out.println("Class name is"+ rc.getName());
        //获取私有的成员属性
        Field name = rc.getDeclaredField("name");
        name.setAccessible(true);
        name.set(o,"王诸宏诗");
        //获取私有的方法
        Method boot = rc.getDeclaredMethod("boot", String.class);
        boot.setAccessible(true);//暴力反射
        boot.invoke(o,"打篮球");//调用方法
        System.out.println(boot);

        Method foot = rc.getMethod("food", String.class);
        foot.invoke(o,"可乐");
        System.out.println(foot);


    }
}
