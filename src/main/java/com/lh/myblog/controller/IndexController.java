package com.lh.myblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.myblog.bean.Blog;
import com.lh.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @GetMapping({"/","/index"})
    public String index(Model model){
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.select("id","title","create_time","first_picture")
                .eq("published",true)
                .orderByDesc("views");
        Page<Blog> page = new Page<>(1,3);
        blogService.page(page,wrapper);
        List<Blog> blogs = page.getRecords();
        model.addAttribute("blogs",blogs);
        return "index";
    }
}
