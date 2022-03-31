package com.lh.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.myblog.bean.Type;

import java.sql.Struct;
import java.util.List;

public interface TypeService extends IService<Type> {

    //通过分类名称查询分类信息
    boolean getByName(String name);
    //查询所有有博客的分类及其博客数量
    List<Type> listAllTypeAndCount();

}
