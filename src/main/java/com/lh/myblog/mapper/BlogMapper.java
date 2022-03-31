package com.lh.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.myblog.bean.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper extends BaseMapper<Blog> {

    //根据传入的条件查询blogs，并分页
    Page<Blog> selectPageBlogs(@Param("page") Page<Blog> page,@Param("blog") Blog blog);

    //分页查询所有已发布博客的各项信息和其分类，并按更新时间倒序展示，如果传入的typeId不为null，则只查询该分类
    Page<Blog> selectPageAllBlogs(Page<Blog> page,@Param("typeId") Integer typeId);

    //根据博客id查询某一篇博客的所有信息
    Blog selectAllInfoById(Long id);

    //根据博客id，让博客的浏览次数+1
    void updateViewsById(Long id);
}
