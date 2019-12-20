package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ApplyResultVO {
    private  Integer id;
    // 会议名称
    private  String title;
    //开始时间
    private String startTime1;
    //结束时间
    private String endTime1;

    // 会议室主键
    private Integer roomId;
    //会议室名称
    private String roomIdent;

    //申请人工号
    private String job_number;

    //申请人姓名
    private String userName;
    // 申请人电话
    private String phone;

    // 审核结果
    private Integer result;

    //拒绝理由
    private String rejectReason;
    //申请提交时间
    private Date applyTime;
    // 批准时间
    private Date approveTime;

    //申请单位
    private String applyDepartment;

    //申请部门负责人工号
    private String depart_job_number;

    //申请部门负责人姓名
    private String departResName;

    //申请部门负责人审核意见
    private Integer depart_suggest;
}
