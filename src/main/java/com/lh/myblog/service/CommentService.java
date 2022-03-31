package com.lh.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.myblog.bean.Blog;
import com.lh.myblog.bean.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    List<Comment> listByBlogId(Long blogId);
}
