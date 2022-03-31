package com.lh.myblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lh.myblog.bean.Blog;
import com.lh.myblog.bean.Comment;
import com.lh.myblog.bean.QQInfo;
import com.lh.myblog.bean.User;
import com.lh.myblog.service.BlogService;
import com.lh.myblog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ReadController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    //在配置文件中指定的默认头像路径
    @Value("${comment.avatar}")
    private String avatar;

    //根据id查询一篇文章的所有需要显示的信息
    @GetMapping("/read/{id}")
    public String read(@PathVariable("id") Long id, Model model){
        Blog blog = blogService.getAllInfoById(id);
        model.addAttribute("blog",blog);
        return "read";
    }

    //根据blogId查询评论
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId,Model model){
        model.addAttribute("comments",commentService.listByBlogId(blogId));
        return "read :: commentList";
    }

    //保存评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        //如果当前session中有管理员登录信息，则将此评论设置博主标记
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAdminComment(true);
        }

        //父评论id前端默认赋值为-1,表示没有父评论，但是如果此评论是点击回复的其他评论，则会被赋值为该评论的id
        Long parentCommentId = comment.getParentCommentId();
        if (parentCommentId == -1){
            comment.setParentCommentId(null);
        }
        //如果该评论用户没有昵称，设置默认昵称
        String nickname = comment.getNickname();
        if (nickname == null || nickname ==""){
            comment.setNickname("无名人士");
        }
        //如果该评论用户没有头像，设置默认头像
        String avatar1 = comment.getAvatar();
        if (avatar1 == null || avatar1 == ""){
            comment.setAvatar(avatar);
        }
        commentService.save(comment);
        return "redirect:/comments/" + comment.getBlogId();
    }

    //删除评论
    @GetMapping("/comments/delete/{blogId}/{commentId}")
    public String deleteComments(@PathVariable("commentId") Long commentId
                                ,@PathVariable("blogId") Long blogId
                                ,HttpSession session){
        //验证是否已登录管理员账号
        User user = (User) session.getAttribute("user");
        if (user != null && user.getId() ==1){
            commentService.removeById(commentId);
        }
        return "redirect:/read/" + blogId;
    }

    //根据qq获取头像、昵称等
    @PostMapping("/qqinfo")
    public String getQQInfo(String qq,Model model){
        if (qq == null || qq.trim() ==""){
            model.addAttribute("qqInfo",null);
        }else {
            QQInfo qqInfo = blogService.getQQInfo(qq);
            model.addAttribute("qqInfo",qqInfo);
        }
        return "read :: qqInfo";
    }
}
