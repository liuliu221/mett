package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.ApplyResult;
import com.example.demo.mapper.ApplyResultMapper;
import com.example.demo.utils.ToDateFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest2 {

    @Autowired
    private ApplyResultMapper mapper;

    @Test
    public void test1(){
        List<ApplyResult> applyResults = mapper.selectList(null);

        if(applyResults!=null && applyResults.size() >0){
            for (ApplyResult applyResult : applyResults) {
                System.out.println(applyResult);
            }
        }
    }

    @Test
    public void testInsert() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start="2019-09-09 17:00:00";
        Date startTime = sdf.parse(start);
        String end = "2019-09-09 18:00:00";
        Date endTime=sdf.parse(end);
        ApplyResult applyResult=new ApplyResult();
          applyResult.setTitle("学生会");
          applyResult.setStartTime(startTime);
          applyResult.setEndTime(endTime);
          //applyResult.setJob_number("1074116101");
          applyResult.setPhone("13893853197");
          applyResult.setResult(0);
          applyResult.setRoomId(1);
          applyResult.setRoomIdent("101");

          mapper.insert(applyResult);

    }

    @Test
    public void test2(){
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("room_id",1);
        queryWrapper.gt("start_time", ToDateFormat.toDate("2019-09-10 00:00:00"));
        List<ApplyResult> applyResults = mapper.selectList(queryWrapper);
        for (ApplyResult applyResult : applyResults) {
            System.out.println(applyResult);
        }
    }
}
