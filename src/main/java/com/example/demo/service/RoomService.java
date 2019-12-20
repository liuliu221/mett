package com.example.demo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Room;
import com.example.demo.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomMapper roomMapper;


    public List<Room> list(){
        return roomMapper.selectList(null);
    }

    public void save(Room room) {
        roomMapper.insert(room);
    }

    public IPage<Room> page(Integer page, Integer limit) {
        Page<Room> page1=new Page<>(page,limit);
        IPage<Room> roomIPage = roomMapper.selectPage(page1, null);
        return roomIPage;
    }

    public Room selectByRoomId(String roomId) {
        return roomMapper.selectById(roomId);
    }
}
