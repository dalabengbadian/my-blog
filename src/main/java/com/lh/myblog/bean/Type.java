package com.lh.myblog.bean;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class Type {

    private Long id;
    private String name;
    @TableField(exist = false)
    private Integer count;//该分类的博客数

    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();//一对多，一个类型可以有多个博客

    public Type(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type(String name) {
        this.name = name;
    }

    public Type() {
    }
}
