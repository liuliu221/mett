package com.example.demo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin selectOne(Admin admin) {
        QueryWrapper<Admin> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",admin.getName());
        queryWrapper.eq("password",admin.getPassword());
        return adminMapper.selectOne(queryWrapper);
    }
}
