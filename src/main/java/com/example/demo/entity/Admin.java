package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Admin {
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    private String name;
    private String password;
}
