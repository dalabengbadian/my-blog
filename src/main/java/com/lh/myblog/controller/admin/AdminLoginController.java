package com.lh.myblog.controller.admin;

import com.lh.myblog.bean.User;
import com.lh.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    //验证账号密码是否正确
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){
        //根据账号密码查询该用户是否存在
        User user = userService.checkUser(username, password);

        if (user != null){
            //为了账号安全，密码置空
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/admin-index";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误!");
            return "redirect:/admin";
        }
    }

    //登出功能
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
