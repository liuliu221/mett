package com.example.demo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest01 {


    @Test
    public void test1(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date1=new Date();

        Date  d  =  new Date(date1.getTime()+24*3600*1000);
        System.out.println(sdf.format(d));

        String f1 = sdf.format(d);
        String[] s = f1.split(" ");
        for(int i =0 ;i<s.length;i++){
            System.out.println(s[i]);
        }
    }
}
