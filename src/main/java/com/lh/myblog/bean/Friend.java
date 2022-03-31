package com.lh.myblog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Friend {

    private Long id;
    private String url;//网址
    private String name;//名称
    private Timestamp createTime;//创建时间
    private String picture;//图片地址
    private String introduce;//介绍
}
