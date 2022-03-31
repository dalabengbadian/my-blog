package com.lh.myblog.controller.admin;

import com.lh.myblog.bean.User;
import com.lh.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/input")
    public String updateUser(HttpSession session){
        return "admin/user-input";
    }

    @PostMapping("/user")
    public String updatePost(User user, RedirectAttributes attributes){
        userService.updateById(user);
        attributes.addFlashAttribute("message","修改个人资料成功");
        return "redirect:/admin/blogs";
    }
}
