package com.lh.myblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lh.myblog.bean.Blog;
import com.lh.myblog.bean.Type;
import com.lh.myblog.service.BlogService;
import com.lh.myblog.service.TypeService;
import org.aspectj.apache.bcel.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.aspectj.apache.bcel.Constants.types;

@Controller
public class BlogsController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    //访问blogs页
    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false) Integer typeId,
                        @RequestParam(defaultValue = "1") Integer blogsPN
                        ,Model model){
        //下面所有关于文章的查询，都需要排除只保存，但不发布的文章
        //分类列表及其博客数量展示
        List<Type> types = typeService.listAllTypeAndCount();
        model.addAttribute("types",types);

        //按浏览次数展示热门文章,只展示8条
        Page<Blog> blogPage = new Page<>(1,8);
        QueryWrapper<Blog> blogQueryWrapper = new QueryWrapper<>();
        blogQueryWrapper.select("id","title")
                        .eq("published",true)
                        .orderByDesc("views");
        blogService.page(blogPage,blogQueryWrapper);
        List<Blog> hotBlogs = blogPage.getRecords();
        model.addAttribute("hotBlogs",hotBlogs);

        //推荐文章展示，只展示5条
        Page<Blog> blogPage1 = new Page<>(1,5);
        QueryWrapper<Blog> blogQueryWrapper1 = new QueryWrapper<>();
        blogQueryWrapper1.select("id","title")
                         .eq("published",true)
                         .eq("recommend",true)
                         .orderByDesc("update_time");
        blogService.page(blogPage1,blogQueryWrapper1);
        List<Blog> recommendBlogs = blogPage1.getRecords();
        model.addAttribute("recommendBlogs",recommendBlogs);

        //中间文章默认按更新时间倒序展示，如果选择分类，则只显示该分类文章
        Page<Blog> blogPage2 = new Page<>(blogsPN,6);
        Page<Blog> blogs = blogService.listPageAllBlogs(blogPage2,typeId);
        model.addAttribute("typeId",typeId);
        model.addAttribute("blogs",blogs);

        //查询已发布文章的总数
        QueryWrapper<Blog> countWrapper = new QueryWrapper<>();
        countWrapper.eq("published",true);
        int count = blogService.count(countWrapper);
        model.addAttribute("count",count);

        return "blogs";
    }

    //跳转搜索页
    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer pn ,String query,Model model){
        Page<Blog> page = new Page<>(pn,6);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        //mysql中and优先级高于or，这里需要满足已发布，然后标题或者介绍满足模糊查询。所以要先计算or再计算and,
        //使用下面方式，lambda表达式的语句先执行
        wrapper.eq("published",true)
                .and(i -> i.like("title",query).or().like("description",query));
        Page<Blog> blogs = blogService.page(page, wrapper);
        model.addAttribute("blogs",blogs);
        model.addAttribute("query",query);
        model.addAttribute("message",query);
        return "search";
    }

    //按分类查看
    @GetMapping("/blogs/{typeId}")
    public String searchPage(@PathVariable("typeId") Integer typeId ,RedirectAttributes attributes){
        attributes.addAttribute("typeId",typeId);
        return "redirect:/blogs";
    }
}
