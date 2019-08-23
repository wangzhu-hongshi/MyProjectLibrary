package com.company.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class JavaBean {
    private String name;
    private int age;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public boolean equals(Object o){
        //判断对象地址是否是一样的
      if(this==o){
          return true;
      }
      if(o==null||this.getClass()!=o.getClass()){
          return false;
      }
      JavaBean javaBean=(JavaBean) o;
      return age==javaBean.age&&Objects.equals(name,javaBean.name);
    }

    public static void main(String[] args) throws ParseException {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(date);
        String str="2019-2-10";
        Date parse = format.parse(str);
        System.out.println(parse);
        System.out.println(format1);
    }

}
