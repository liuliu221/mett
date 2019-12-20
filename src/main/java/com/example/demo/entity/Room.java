package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Room {

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;
    private String ident;
    private int capacity;

    private String location;

}
