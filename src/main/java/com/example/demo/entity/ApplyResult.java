package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
@TableName("applyresult")
public class ApplyResult {

   //主键
   @TableId(value = "ID", type = IdType.AUTO)
    private  Integer id;
    // 会议名称
    private  String title;
    //开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;
    //结束时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

    // 会议室主键
    private Integer roomId;
    //会议室名称
    private String roomIdent;

    //申请人工号
    private String jobNumber;

    //申请人姓名
    private String userName;
    // 申请人电话
    private String phone;

    // 审核结果
    private Integer result;

    //拒绝理由
    private String rejectReason;
    //申请提交时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyTime;
      // 批准时间
      @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date approveTime;

    //申请部门

    private String applyDepartment;

    //申请部门负责人工号
    private String departJobNumber;

    //申请部门负责人姓名

     private String departResName;

    //申请部门负责人意见
    private Integer departSuggest;
}
