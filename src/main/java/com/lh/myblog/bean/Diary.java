package com.lh.myblog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
@AllArgsConstructor
public class Diary {

    private Long id;
    private String content; //日记内容
    private Timestamp createTime;

    public Diary() {
    }

}
