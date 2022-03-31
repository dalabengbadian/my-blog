package com.lh.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.myblog.bean.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeMapper extends BaseMapper<Type> {

    //查询所有有博客的分类，并按博客数量排序
    List<Type> selectAllTypeAndCount();
}
