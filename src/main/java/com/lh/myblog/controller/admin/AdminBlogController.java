package com.lh.myblog.controller.admin;


import com.alibaba.druid.sql.visitor.functions.Now;
import com.alibaba.druid.sql.visitor.functions.Substring;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.myblog.bean.Blog;
import com.lh.myblog.bean.Type;
import com.lh.myblog.bean.User;
import com.lh.myblog.service.BlogService;
import com.lh.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Value("${pictureSave}")
    private String pictureSave;  //配置文件中配置的图床地址

    //博客列表页
    @GetMapping("/blogs")
    public String blogs(@RequestParam(value = "pageNumber" , defaultValue = "1") Integer pageNumber, Blog blog, Model model){
        Page<Blog> page = new Page<>(pageNumber, 5);
        //查询所有blog
        blogService.listBlogs(page, blog);
        //查询所有分类，用于在下拉框中选择
        List<Type> types = typeService.list();
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        return "admin/blogs";
    }

    //根据条件搜索博客
    @PostMapping("/blogs/search")
    public String searchBlogs(@RequestParam(value = "pageNumber" , defaultValue = "1") Integer pageNumber, Blog blog, Model model){

        Page<Blog> page = new Page<>(pageNumber, 5);
        //查询所有blog
        blogService.listBlogs(page, blog);
        //查询所有分类，用于在下拉框中选择
        List<Type> types = typeService.list();
        model.addAttribute("page",page);
        model.addAttribute("types",types);
        return "admin/blogs :: blogList";//此请求只用于后台查询博客时，异步更新表格中数据
    }

    //新增博客编辑发布
    @GetMapping("/blogs/input")
    public String addInput(Model model){
        //查询出所有分类，用于在下拉框中选择
        List<Type> types = typeService.list();
        model.addAttribute("types",types);
        return "admin/blogs-input";
    }

    //提交或者保存新增的博客
    @PostMapping("/blogs")
    public String addPost(Blog blog,Model model, RedirectAttributes attributes, HttpSession session){
        //查询该博客标题是否存在
        Blog blog1 = blogService.getByTitle(blog.getTitle());
        if (blog1 != null){
            model.addAttribute("message","提交/保存失败，标题《" + blog.getTitle() + "》已存在");
            model.addAttribute("blog",blog);
            return "admin/blogs-input";
        }else {
            //为首图地址拼接上阿里云oss对象存储地址的前缀
            String firstPicture = blog.getFirstPicture();
            if (firstPicture != null) {
                String firstPicture1 = pictureSave + firstPicture;
                blog.setFirstPicture(firstPicture1);
            }
            //获取当前session中存放的用户信息
            User user =(User)session.getAttribute("user");
            blog.setUserId(user.getId());
            boolean save = blogService.save(blog);
            if (save){
            attributes.addFlashAttribute("message","提交/保存成功");
            return "redirect:/admin/blogs";
            }else {
                model.addAttribute("message","提交/保存失败");
                model.addAttribute("blog",blog);
                return "admin/blogs-input";
            }
        }
    }

    //点击编辑，跳转到修改博客页面
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        Blog blog = blogService.getById(id);
        //删除首图地址拼接的阿里云oss对象存储地址的前缀，方便显示在前端页面
        String firstPicture = blog.getFirstPicture();
        if (firstPicture != null  && firstPicture.length() >= pictureSave.length()) {
            String firstPicture1 = firstPicture.substring(pictureSave.length());  //去除图床地址前缀，截取后面部分
            blog.setFirstPicture(firstPicture1);
        }
        model.addAttribute("blog",blog);
        //查询出所有分类，用于在下拉框中选择
        List<Type> types = typeService.list();
        model.addAttribute("types",types);
        return "admin/blogs-input";
    }

    //提交/保存修改的博客
    @Transactional
    @PostMapping("/blogs/{id}")
    public String editPost(Blog blog,RedirectAttributes attributes,Model model){
        //如果修改了博客标题，验证修改后的标题是否已存在，但是需要排除标题未修改的情况
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("title",blog.getTitle())
                .ne("id",blog.getId());
        Blog one = blogService.getOne(wrapper);
        if (one != null){
            model.addAttribute("message","提交/保存失败，标题《" + blog.getTitle() + "》已存在");
            model.addAttribute("blog",blog);
            return "admin/blogs-input";
        }else {
            //为首图地址拼接上阿里云oss对象存储地址的前缀
            String firstPicture = blog.getFirstPicture();
            if (firstPicture != null) {
                String firstPicture1 = pictureSave + firstPicture;
                blog.setFirstPicture(firstPicture1);
            }
            //设置当前时间为更新时间。这里不使用数据库的自动更新时间是为了防止更新浏览次数时自动更新时间
            blog.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            boolean update = blogService.updateById(blog);
            if (update){
            attributes.addFlashAttribute("message","修改博客《" + blog.getTitle() + "》成功");
            return "redirect:/admin/blogs";
            }else {
                model.addAttribute("message","提交/保存失败");
                model.addAttribute("blog",blog);
                return "admin/blogs-input";
            }

        }
    }

    //删除博客
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id,RedirectAttributes attributes){
        boolean remove = blogService.removeById(id);
        if (remove){
            attributes.addFlashAttribute("message","删除成功");
        }else {
            attributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/blogs";
    }
}
