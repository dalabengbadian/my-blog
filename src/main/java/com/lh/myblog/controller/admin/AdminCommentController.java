package com.lh.myblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.myblog.bean.Comment;
import com.lh.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lh
 * @create 2022-03-31 11:51
 */
@Controller
@RequestMapping("/admin")
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 按时间倒序显示评论，方便管理员查看回复最新评论
     * @return
     */
    @GetMapping("/comments")
    private String listComment(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber, Model model){
        Page<Comment> page = new Page<>(pageNumber, 5);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        commentService.page(page,wrapper);
        model.addAttribute("page",page);
        return "admin/comments";
    }

    /**
     * 翻页
     * @return
     */
    @PostMapping("/comments/page")
    private String listCommentPage(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber, Model model){
        Page<Comment> page = new Page<>(pageNumber, 5);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        commentService.page(page,wrapper);
        model.addAttribute("page",page);
        return "admin/comments :: commentsPage";
    }

}
