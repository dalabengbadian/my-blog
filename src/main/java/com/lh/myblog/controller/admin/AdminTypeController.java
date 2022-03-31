package com.lh.myblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.myblog.bean.Blog;
import com.lh.myblog.bean.Type;
import com.lh.myblog.service.BlogService;
import com.lh.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminTypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;


    //显示Type分页列表
    @GetMapping("/types")
    public String list(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, Model model) {
        //构造分页参数
        Page<Type> page = new Page<>(pageNumber, 5);
        //调用page方法进行分页
        typeService.page(page);
        //将分页信息存入model
        model.addAttribute("page", page);
        return "admin/types";
    }

    //分页列表翻页
    @PostMapping("/types/page")
    public String listPage(@RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, Model model) {
        //构造分页参数
        Page<Type> page = new Page<>(pageNumber, 5);
        //调用page方法进行分页
        typeService.page(page);
        //将分页信息存入model
        model.addAttribute("page", page);
        return "admin/types :: typesPage";
    }

    //跳转新增分类页
    @GetMapping("/types/input")
    public String addInput() {
        return "admin/types-input";
    }

    //提交新增分类
    @PostMapping("/types")
    public String addPost(Type type, RedirectAttributes attributes) {
        //查询该分类是否已存在于数据库
        boolean name = typeService.getByName(type.getName());
        if (name) {
            attributes.addFlashAttribute("message", "新增失败,该分类已存在");
        } else {
            boolean save = typeService.save(type);

            if (save) {
                attributes.addFlashAttribute("message", "新增成功");
            } else {
                attributes.addFlashAttribute("message", "新增失败");
            }
        }
        return "redirect:/admin/types";
    }

    //点击编辑，跳转到修改分类页面
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getById(id));
        return "admin/types-input";
    }

    //提交修改的分类
    @PostMapping("/types/{id}")
    public String editPost(Type type, @PathVariable Long id, RedirectAttributes attributes) {
        //查询该分类是否已存在于数据库
        boolean name = typeService.getByName(type.getName());
        if (name) {
            attributes.addFlashAttribute("message", "修改失败,该分类已存在");
        } else {
            type.setId(id);
            boolean update = typeService.updateById(type);
            if (update) {
                attributes.addFlashAttribute("message", "修改成功");
            } else {
                attributes.addFlashAttribute("message", "修改失败");
            }
        }
        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes) {
        //查询当前分类下是否还有博客
        List<Blog> blogs = blogService.listByTypeId(id);
        if (blogs.isEmpty()) {
            boolean b = typeService.removeById(id);
            if (b) {
                attributes.addFlashAttribute("message", "删除成功");
            } else {
                attributes.addFlashAttribute("message", "删除失败");
            }
        }else {
            attributes.addFlashAttribute("message","删除失败，当前分类下还有博客文章，请先管理博客");
        }
        return "redirect:/admin/types";
    }
}
