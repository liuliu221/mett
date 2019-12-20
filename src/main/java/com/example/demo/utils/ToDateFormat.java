package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToDateFormat {

     private  static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


   public static String toStringFormat(Date date){
          return sdf.format(date);
    }

    public static Date toDate(String str){
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  Date  toSubstractTwoDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        return calendar.getTime();
    }

}
