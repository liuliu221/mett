package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import com.example.demo.utils.Msg;
import com.example.demo.utils.ResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController  {

    @Autowired
    private RoomService roomService;

    @RequestMapping("/list")
    public List<Room> listRoom(){
        return  roomService.list();
    }

    @PostMapping("/save")
    public void save(@RequestBody Room room){
        roomService.save(room);
    }


    @RequestMapping("/page")
    public List<Room> page(@RequestParam(value = "page",defaultValue = "1") Integer page,
                           @RequestParam(value = "limit",defaultValue = "10") Integer limit){

        IPage<Room> page1 = roomService.page(page, limit);
        return page1.getRecords();
    }

    @RequestMapping("/page2")
    public ResultMsg<List> page2(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                @RequestParam(value = "limit",defaultValue = "10") Integer limit){
        IPage<Room> page1 = roomService.page(page, limit);
        List<Room> records = page1.getRecords();


        ResultMsg<List> resultMsg=ResultMsg.success();
        resultMsg.setCount(page1.getTotal());
        resultMsg.setMsg("");
        resultMsg.setData(records);
        return resultMsg;
    }

    @GetMapping("/info/{roomId}")
    public Room info(@PathVariable String roomId){
        return  roomService.selectByRoomId(roomId);
    }
}
