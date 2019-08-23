package com.company.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DateDemo {
    public static void main(String[] args) throws ParseException {
        System.out.println("请输入出生日期 格式 YYYY-MM-dd");
        Scanner sc=new Scanner(System.in);
        String next = sc.next();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date parse = format.parse(next);
        long time = parse.getTime();

        Date date=new Date();
        long time1 = date.getTime();

        long secone=time1-time;
        if(secone<0){
            System.out.println("还没出生呢");
        }else {
            System.out.println(secone/1000/60/60/24);
        }
    }
}
