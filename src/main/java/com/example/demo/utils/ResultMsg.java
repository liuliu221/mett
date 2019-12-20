package com.example.demo.utils;

public class ResultMsg<T> {

     private  int code;
     private String msg;
     private long count;

     private T data;


     public ResultMsg(){

     }

     public ResultMsg(int code){
         this.code=code;
     }

     public static ResultMsg success(){
         return new ResultMsg(0);
     }

     public void setCount(long count){
           this.count=count;
     }

     public long getCount(){
         return count;
     }

     public  void setCode(int code){
         this.code=code;
     }

     public int getCode(){
         return code;
     }

     public void setMsg(String msg){
         this.msg=msg;
     }

     public String getMsg(){
         return msg;
     }

     public void setData(T data){
         this.data=data;
     }

    public T getData() {
        return data;
    }
}
