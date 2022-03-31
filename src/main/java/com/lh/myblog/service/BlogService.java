package com.lh.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.myblog.bean.Blog;
import com.lh.myblog.bean.QQInfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface BlogService extends IService<Blog> {
    /**
     * 根据传入的条件查询博客并分页展示
     * @param page：分页参数
     * @param blog：条件封装的实体
     * @return
     */
    Page<Blog> listBlogs(Page<Blog> page,Blog blog);

    /**
     * 根据typeId查询博客
     * @param typeId
     * @return
     */
    List<Blog> listByTypeId(Long typeId);

    /**
     * 根据标题查询博客
     * @param title
     * @return
     */
    Blog getByTitle(String title);

    /**
     * 分页查询所有已发布博客的各项信息和其分类，并按更新时间倒序展示，如果传入的typeId不为null，则只查询该分类
     * @return
     */
    Page<Blog> listPageAllBlogs(Page<Blog> page,Integer typeId);

    /**
     * 根据博客id查询某一篇博客的所有信息,和分类名称，用户名称，并将文章内容转换为HTML格式
     * @param id
     * @return
     */
    Blog getAllInfoById(Long id);


    /**
     * 根据qq获取头像和昵称
     * @param qqId
     * @return
     * @throws IOException
     */
    QQInfo getQQInfo(String qqId);
}
