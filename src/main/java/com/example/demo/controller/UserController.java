package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Msg login(@RequestBody User user){


        Msg<String> msg=new Msg();
        User user1 = userService.selectOne(user);
        if(user1!=null){
            msg.setCode("200");
            msg.setData(user1.getName());
        }else{

             msg.setCode("700");
             msg.setData("用户名或密码错误！");

        }
        return msg;
    }


    @PostMapping("/saveOrUpdate/{jobNumber}")
    public Msg saveOrUpdate(@PathVariable String jobNumber){
         if( userService.saveOrUpdate(jobNumber) ==1){
             return Msg.success();
         }else{
             Msg<String> msg=new Msg();
             msg.setCode("700");
             msg.setData("用户添加或更改密码失败！");
             return msg;
         }
    }

    @PostMapping("/updatePw")
    public Msg changePassword(@RequestBody User user){
        System.out.println(user);
        if(userService.updatePassword(user)==1){
            return Msg.success();
        }else{
            Msg<String> msg=new Msg();
            msg.setCode("700");
            msg.setData("更改密码失败！");
            return msg;
        }
    }

    @PostMapping("nameSearch/{jobNumber}")
    public Msg nameSearch(@PathVariable String jobNumber){
        System.out.println(jobNumber);
         User user= userService.selectByJobNumber(jobNumber);
        Msg<String> msg=new Msg();
         if(user!= null){
             msg.setCode("200");
             msg.setData(user.getName());
         }else{
             msg.setCode("700");
             msg.setData("");
         }

         return msg;
    }

  }
