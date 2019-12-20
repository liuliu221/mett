package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {

    @TableId(value = "ID", type = IdType.AUTO)
    private int id;
    private String name;
    private String jobNumber;
    private String password;

}
