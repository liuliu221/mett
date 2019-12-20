package com.example.demo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ApplyResult;
import com.example.demo.entity.Room;
import com.example.demo.mapper.ApplyResultMapper;
import com.example.demo.utils.Msg;
import com.example.demo.utils.ToDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplyService {

    @Autowired
    private ApplyResultMapper applyResultMapper;

    public List<ApplyResult> listByRoomId(String roomId){
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("room_id",roomId);
        List<ApplyResult> applyResults = applyResultMapper.selectList(queryWrapper);
        return applyResults;
    }

    public List<ApplyResult> listByRoomIdWithTime(String roomId){
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("room_id",roomId);
        queryWrapper.gt("start_time", ToDateFormat.toSubstractTwoDay());
        List<ApplyResult> applyResults = applyResultMapper.selectList(queryWrapper);
        return applyResults;
    }

    public  List<ApplyResult> listByRoomIdWithTime1(String roomId){
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("room_id",roomId);
        queryWrapper.gt("start_time", ToDateFormat.toSubstractTwoDay());
        queryWrapper.ne("result",2);
//        queryWrapper.ne("depart_suggest",2);

//        queryWrapper.and(wrapper -> wrapper.eq("room_id", roomId)
//                .gt("start_time",ToDateFormat.toSubstractTwoDay())
//                .ne("depart_suggest",2)
//        );

        List<ApplyResult> applyResults = applyResultMapper.selectList(queryWrapper);
        List<ApplyResult> collect = applyResults.stream().filter(item -> item.getResult() != 2).collect(Collectors.toList());
        return collect;
    }

    public int insert(ApplyResult applyResult) {
        //做时间上的验证 保证不发生时间上的冲突
       // checkTime(applyResult);
        //return applyResultMapper.insert(applyResult);
        if(checkTime(applyResult)){
            return applyResultMapper.insert(applyResult);
        }
        //代表有时间上的冲突
        return 11;
    }

    public boolean checkTime(ApplyResult applyResult){
        Date startTime = applyResult.getStartTime();
        String startTimeStringFormat = ToDateFormat.toStringFormat(startTime);
        String dataString = startTimeStringFormat.substring(0, 10);
        String startTimeString=dataString+" 00:00:00";
        String endtimeString=dataString+" 23:59:59";
        Date startTime1=ToDateFormat.toDate(startTimeString);
        Date endTime1=ToDateFormat.toDate(endtimeString);

        //寻找当天的日程
        List<ApplyResult> list=findList(startTime1,endTime1,applyResult.getRoomId());

        if(list!=null && list.size()>0){
            //doCheck();
           return  doCheck(list , applyResult);
        }

        return true;
    }

    public boolean doCheck(List<ApplyResult> list, ApplyResult applyResult){
        //先找申请时间的 长整型
        long startTimeLong = applyResult.getStartTime().getTime();
        long endTimeLong = applyResult.getEndTime().getTime();
        //分三种情况： 1. 在头部， 2.在尾部， 3.在中间
        //判断第一种情况
        if( endTimeLong< list.get(0).getEndTime().getTime()){
            return true;
        }

        //判断第二种情况
        if(startTimeLong > list.get(list.size()-1).getEndTime().getTime()){
            return true;
        }
        //判断在中间
        for(int i = 0 ; i<list.size()-1 ; i++){
            if(i!= list.size()-1){
                if(startTimeLong > list.get(i).getEndTime().getTime() &&
                   endTimeLong< list.get(i+1).getStartTime().getTime()){
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    //查询当日 该会议室的所有 会议议程安排
    // 要当日申请中或批准的 ， 不要 审批不通过的
    //排列上 时间要升序的
    public List<ApplyResult> findList(Date startTime1,Date endTime1,Integer roomId){
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId);
        queryWrapper.gt("start_time",startTime1);
        queryWrapper.lt("end_time",endTime1);
        queryWrapper.ne("result",2);

        queryWrapper.orderByAsc("start_time");

        List<ApplyResult> list= applyResultMapper.selectList(queryWrapper);
        List<ApplyResult> collect = list.stream().filter(item -> item.getDepartSuggest() != 2).collect(Collectors.toList());
        list.sort((a,b) ->
            Long.compare(a.getStartTime().getTime(),b.getStartTime().getTime())
        );
        return collect;
    }

    public IPage<ApplyResult> page(Integer page, Integer limit,String job_number,Integer result) {
        Page page1=new Page(page,limit);
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();


        if(job_number !=null  && job_number.trim().length() >0){
            queryWrapper.eq("job_number",job_number);
        }else{
            System.out.println("job_number is null");
        }

        if( null != result){
             queryWrapper.ne("result",0);
        }else{
             queryWrapper.eq("result",0);
        }
        queryWrapper.orderByDesc("apply_time");

        IPage<ApplyResult> iPage = applyResultMapper.selectPage(page1, queryWrapper);
        return iPage;
    }


    public IPage<ApplyResult> page2(Integer page, Integer limit,String job_number,Integer result) {
        Page page1=new Page(page,limit);
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();


        if(job_number !=null  && job_number.trim().length() >0){
            queryWrapper.eq("job_number",job_number);
        }else{
            System.out.println("job_number is null");
        }

//        if( null != result){
//            queryWrapper.ne("result",0);
//        }else{
//            queryWrapper.eq("result",0);
//        }
        queryWrapper.orderByDesc("apply_time");

        IPage<ApplyResult> iPage = applyResultMapper.selectPage(page1, queryWrapper);
        return iPage;
    }

    public Msg approve(String ids) {
        System.out.println(ids);
        ApplyResult applyResult = applyResultMapper.selectById(ids);
        if(applyResult !=null && applyResult.getResult() ==0){
            applyResult.setResult(1);
            applyResult.setApproveTime(new Date());
            int i = applyResultMapper.updateById(applyResult);
            if(i ==1){
                return Msg.success();
            }
        }
        return  new Msg();
    }

    public Msg disapprove(String ids) {
        System.out.println(ids);
        ApplyResult applyResult = applyResultMapper.selectById(ids);
        if(applyResult !=null && applyResult.getResult() ==0){
            applyResult.setResult(2);
            applyResult.setApproveTime(new Date());
            int i = applyResultMapper.updateById(applyResult);
            if(i ==1){
                return Msg.success();
            }
        }
        return  new Msg();
    }

    public IPage<ApplyResult> pageAudit(Integer page, Integer limit) {
        Page page1=new Page(page,limit);
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();
        queryWrapper.ne("result",0);


        queryWrapper.orderByDesc("start_time");

        IPage<ApplyResult> iPage = applyResultMapper.selectPage(page1, queryWrapper);
        return iPage;
    }

    public IPage<ApplyResult> pageWithResultStatusZero(Integer page, Integer limit) {
        Page page1=new Page(page,limit);
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("result",0);
        queryWrapper.orderByDesc("apply_time");
        IPage<ApplyResult> iPage = applyResultMapper.selectPage(page1, queryWrapper);
        return iPage;

    }

    public IPage<ApplyResult> pageWithDepartSuggestAndResult(Integer page, Integer limit, String job_number) {

        //用户查看审批结果
        // 该用户工号下的 所有的都能看到
        Page page1=new Page(page,limit);
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("job_number",job_number);

        queryWrapper.orderByDesc("apply_time");

        IPage<ApplyResult> iPage = applyResultMapper.selectPage(page1, queryWrapper);
        return iPage;


    }

    public IPage<ApplyResult> pageWithDepartSuggest(Integer page, Integer limit, String job_number) {
        //申请部门负责人审批
        // 该用户工号下的 所有的都能看到
        System.out.println(job_number);
        Page page1=new Page(page,limit);
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("depart_job_number",job_number);
        queryWrapper.eq("depart_suggest",0);

        queryWrapper.orderByDesc("apply_time");

        IPage<ApplyResult> iPage = applyResultMapper.selectPage(page1, queryWrapper);
        return iPage;
    }

    public Msg departApprove(String ids) {
        System.out.println(ids);
        ApplyResult applyResult = applyResultMapper.selectById(ids);
        if(applyResult !=null && applyResult.getDepartSuggest() ==0){
            applyResult.setDepartSuggest(1);
            // applyResult.setApproveTime(new Date());
            int i = applyResultMapper.updateById(applyResult);
            if(i ==1){
                return Msg.success();
            }
        }
        return  new Msg();
    }

    public Msg departDisapprove(String ids) {
        System.out.println(ids);
        ApplyResult applyResult = applyResultMapper.selectById(ids);
        if(applyResult !=null && applyResult.getDepartSuggest() ==0){
            applyResult.setDepartSuggest(2);
            // applyResult.setApproveTime(new Date());
            int i = applyResultMapper.updateById(applyResult);
            if(i ==1){
                return Msg.success();
            }
        }
        return  new Msg();
    }

    public IPage<ApplyResult> pageWithAdminBeforeApprove(Integer page, Integer limit) {
        Page page1=new Page(page,limit);
        QueryWrapper<ApplyResult> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("depart_suggest",1);
        queryWrapper.eq("result",0);
        queryWrapper.orderByDesc("apply_time");
        IPage<ApplyResult> iPage = applyResultMapper.selectPage(page1, queryWrapper);
        return iPage;
    }
}
