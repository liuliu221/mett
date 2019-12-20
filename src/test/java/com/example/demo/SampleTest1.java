package com.example.demo;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.ApplyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest1 {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private ApplyService applyService;

    @Test
    public void testUser(){

        List<User> userList = userMapper.selectList(null);
        for(User user:userList){
              userList.forEach(System.out::println);
        }
    }

    @Test
    public void testSelectUserByJobNumberAndPassword(){
           QueryWrapper<User>  queryWrapper=new QueryWrapper<>();
           queryWrapper.eq("job_number","1074116101");
           queryWrapper.eq("password","a123456");
        User user = userMapper.selectOne(queryWrapper);
        if(user!=null){
            System.out.println(user);
        }
    }

    @Test
    public void test4(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("job_number","1074116101");
        queryWrapper.eq("password","123456");
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            System.out.println("用户名或密码不对");
        }
    }

    @Test
    public void test1(){
        List<Admin> userList = adminMapper.selectList(null);

        userList.forEach(System.out::println);
    }

    @Test
    public void test2(){
        QueryWrapper<Admin> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("name","admin");
        queryWrapper.eq("password","admin");
        Admin admin = adminMapper.selectOne(queryWrapper);
        if(admin != null){
            System.out.println(admin.getId());
            System.out.println(admin.getName());
            System.out.println(admin.getPassword());
        }
    }


    @Test
    public void test3(){
           Wrapper<Admin> wrapper;
    }


    @Test
    public void testPage(){
        System.out.println("----- baseMapper 自带分页 ------");
        Page<Room> page = new Page<>(2, 5);
        IPage<Room> roomIPage = roomMapper.selectPage(page, null);

        System.out.println("总条数 ------> " + roomIPage.getTotal());
        System.out.println("当前页数 ------> " + roomIPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + roomIPage.getSize());
        for(Room room:roomIPage.getRecords()){
            System.out.println(room);
        }
        System.out.println("----- baseMapper 自带分页 ------");
    }

    @Test
    public  void testR(){
      applyService.listByRoomIdWithTime1("1");
    }
}
