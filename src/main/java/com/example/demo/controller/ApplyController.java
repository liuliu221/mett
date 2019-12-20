package com.example.demo.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Apply;
import com.example.demo.entity.ApplyResult;
import com.example.demo.service.ApplyService;
import com.example.demo.utils.Msg;
import com.example.demo.utils.ResultMsg;
import com.example.demo.utils.ToDateFormat;
import com.example.demo.vo.ApplyResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;


    @RequestMapping("/result")
    public List<Apply> listByRoomId(@RequestParam("roomId") String roomId) {
        List<ApplyResult> applyResults = applyService.listByRoomIdWithTime(roomId);
        List<Apply> list = new ArrayList<>();
        for (ApplyResult applyResult : applyResults) {
            Apply apply = new Apply();
            apply.setStart(applyResult.getStartTime());
            apply.setEnd(applyResult.getEndTime());
            apply.setTitle(applyResult.getTitle());
            list.add(apply);
        }

        return list;
    }

    //查询该会议室的日程
    @RequestMapping("/resultNew")
    public List<Apply> listByRoomIdNew(@RequestParam("roomId") String roomId) {
        List<ApplyResult> applyResults = applyService.listByRoomIdWithTime1(roomId);
        List<Apply> list = new ArrayList<>();
        for (ApplyResult applyResult : applyResults) {
            Apply apply = new Apply();
            apply.setStart(applyResult.getStartTime());
            apply.setEnd(applyResult.getEndTime());
            apply.setTitle(applyResult.getTitle());
            list.add(apply);
        }

        return list;
    }



    @RequestMapping("/result2")
    public List<Apply> list(@RequestParam("roomId") String roomId) throws ParseException {
        List<Apply> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = "2019-09-09 17:00:00";
        Date startTime = sdf.parse(start);
        String end = "2019-09-09 18:00:00";
        Date endTime = sdf.parse(end);

        Apply apply = new Apply();
        apply.setStart(startTime);
        apply.setEnd(endTime);
        apply.setTitle("学生会");

        list.add(apply);
        return list;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Msg insert(@RequestBody ApplyResultVO applyResultVO) {
        //一进来要处理两个数据 applyTime 和 result
        System.out.println(applyResultVO);

        applyResultVO.setApplyTime(new Date());
        ApplyResult applyResult = new ApplyResult();
        BeanUtils.copyProperties(applyResultVO, applyResult);


        Date date1 = ToDateFormat.toDate(applyResultVO.getStartTime1());

        applyResult.setStartTime(date1);
        applyResult.setEndTime(ToDateFormat.toDate(applyResultVO.getEndTime1()));

        applyResult.setDepartJobNumber(applyResultVO.getDepart_job_number());
        applyResult.setDepartSuggest(applyResultVO.getDepart_suggest());
        applyResult.setJobNumber(applyResultVO.getJob_number());

        System.out.println("---------------");
        System.out.println(applyResult);

        if (applyService.insert(applyResult) == 1) {
            return Msg.success();
        }else if(applyService.insert(applyResult) ==11){
             Msg<String> msg=new Msg();
             msg.setCode("701");
             msg.setData("该会议室安排有时间有冲突");

            // System.out.println("11111111111111111");
            return msg;
        }


        Msg msg = new Msg();
        msg.setCode("401");
        return msg;
    }

    @GetMapping("/page")
    public ResultMsg<List> page(@RequestParam(value = "job_number", defaultValue = "") String job_number,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        IPage<ApplyResult> page1 = applyService.page2(page, limit, job_number,null);
        List<ApplyResult> records = page1.getRecords();

        ResultMsg<List> resultMsg = ResultMsg.success();
        resultMsg.setCount(page1.getTotal());
        resultMsg.setMsg("");
        resultMsg.setData(records);
        return resultMsg;
    }

    @GetMapping("/pageWithDepartSuggestAndResult")
    public ResultMsg<List> pageWithDepartSuggestAndResult(@RequestParam(value = "job_number", defaultValue = "") String job_number,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        IPage<ApplyResult> page1 = applyService.pageWithDepartSuggestAndResult(page, limit, job_number);
        List<ApplyResult> records = page1.getRecords();

        for (ApplyResult result:records) {
             if(result.getId()>23){
                 System.out.println(result);
             }
        }

        ResultMsg<List> resultMsg = ResultMsg.success();
        resultMsg.setCount(page1.getTotal());
        resultMsg.setMsg("");
        resultMsg.setData(records);
        return resultMsg;
    }

    @PostMapping("/approve")
    public Msg approve(String ids){
         return  applyService.approve(ids);
    }

    @PostMapping("/disapprove")
    public Msg disapprove(String ids) {
         return  applyService.disapprove(ids);
    }


    @GetMapping("/listWithApprove")
    public ResultMsg<List> toList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                      @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        IPage<ApplyResult> page1 = applyService.page(page, limit, null,1);
        List<ApplyResult> records = page1.getRecords();

        ResultMsg<List> resultMsg = ResultMsg.success();
        resultMsg.setCount(page1.getTotal());
        resultMsg.setMsg("");
        resultMsg.setData(records);
        return resultMsg;
    }

    @GetMapping("/listWithAudit")
    public ResultMsg<List> toListAduit(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        IPage<ApplyResult> page1 = applyService.pageAudit(page, limit);
        List<ApplyResult> records = page1.getRecords();

        ResultMsg<List> resultMsg = ResultMsg.success();
        resultMsg.setCount(page1.getTotal());
        resultMsg.setMsg("");
        resultMsg.setData(records);
        return resultMsg;
    }

    @GetMapping("/pageWithZero")
    public ResultMsg<List> pageWithResultStatusZero(@RequestParam(value = "job_number", defaultValue = "") String job_number,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        IPage<ApplyResult> page1 = applyService.pageWithResultStatusZero(page, limit);
        List<ApplyResult> records = page1.getRecords();

        ResultMsg<List> resultMsg = ResultMsg.success();
        resultMsg.setCount(page1.getTotal());
        resultMsg.setMsg("");
        resultMsg.setData(records);
        return resultMsg;
    }

    @GetMapping("/pageWithAdminBeforeApprove")
    public ResultMsg<List> pageWithAdminBeforeApprove(
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        IPage<ApplyResult> page1 = applyService.pageWithAdminBeforeApprove(page, limit);
        List<ApplyResult> records = page1.getRecords();

        ResultMsg<List> resultMsg = ResultMsg.success();
        resultMsg.setCount(page1.getTotal());
        resultMsg.setMsg("");
        resultMsg.setData(records);
        return resultMsg;
    }

    @GetMapping("/pageWithDepartSuggest")
    public ResultMsg<List> pageWithDepartSuggest(@RequestParam(value = "job_number", defaultValue = "") String job_number,
                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(value = "limit", defaultValue = "10") Integer limit){
       //申请部门负责人查看待审批表
        IPage<ApplyResult> page1 = applyService.pageWithDepartSuggest(page, limit,job_number);
        List<ApplyResult> records = page1.getRecords();

        ResultMsg<List> resultMsg = ResultMsg.success();
        resultMsg.setCount(page1.getTotal());
        resultMsg.setMsg("");
        resultMsg.setData(records);
       // System.out.println(records.get(0));
        return resultMsg;
    }

    @PostMapping("/departApprove")
    public Msg departApprove(String ids){
        return  applyService.departApprove(ids);
    }

    @PostMapping("/departDisapprove")
    public Msg departDisapprove(String ids) {
         return applyService.departDisapprove(ids);
    }
}
