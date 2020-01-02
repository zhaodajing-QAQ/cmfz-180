package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner implements Serializable {

  @Excel(name = "编号")
  private String id;
  @Excel(name = "标题")
  private String title;
  @Excel(name = "图片",type = 2,height = 30,width = 50)
  private String img;
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "日期",format = "yyyy-MM-dd")
  private Date create_date;
  @Excel(name = "状态")
  private String status;
  private String other;
}
