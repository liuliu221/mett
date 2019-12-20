package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User selectOne(User user) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("job_number",user.getJobNumber());
        queryWrapper.eq("password",user.getPassword());
        User user1 = userMapper.selectOne(queryWrapper);
       return user1;
    }

    @Transactional
    public int saveOrUpdate(String jobNumber) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("job_number",jobNumber);
        User user=userMapper.selectOne(queryWrapper);
        if(user!=null){
            user.setPassword("a123456");
            return  userMapper.updateById(user);
        }else{
            User u=new User();
            u.setJobNumber(jobNumber);
            u.setPassword("a123456");
            return userMapper.insert(u);
        }
    }

    public int updatePassword(User user) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("job_number",user.getJobNumber());
         return userMapper.update(user,queryWrapper);
    }

    public User selectByJobNumber(String jobNumber) {

          QueryWrapper<User> queryWrapper=new QueryWrapper<>();

          queryWrapper.eq("job_number",jobNumber);
          return userMapper.selectOne(queryWrapper);

    }
}
