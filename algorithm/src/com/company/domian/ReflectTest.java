package com.company.domian;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class ReflectTest {
    public static void main(String[] args) throws Exception {

        Properties propertie=new Properties();
        InputStream resourceAsStream = ReflectTest.class.getClassLoader().getResourceAsStream("pro.properties");
        propertie.load(resourceAsStream);
        String calssName = propertie.getProperty("calssName");
        String methodName = propertie.getProperty("methodName");

        Class aClass = Class.forName(calssName);
        Object obj = aClass.newInstance();
        Method method = aClass.getMethod(methodName);
        method.invoke(obj);

    }
}
