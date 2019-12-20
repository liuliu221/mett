package com.example.demo.utils;

import lombok.Data;

@Data
public class Msg<T> {

    private String code;

    private T data;


    public Msg(){

    }

    public Msg(String code){
          this.code=code;
    }

    public static Msg success(){
         return new Msg("200");
    }

}
