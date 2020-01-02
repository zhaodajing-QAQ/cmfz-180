package com.baizhi.entity;

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
public class Album implements Serializable {

  private String id;                      //id
  private String title;                   //标题
  private String img;                     //图片
  private String score;                   //评分
  private String author;                  //作者
  private String broadcaster;             //播音员
  private String count;                   //数量
  private String brief;                   //简介
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date create_Date;               //日期
  private String status;                  //状态
  private String other;                   //其他
}
