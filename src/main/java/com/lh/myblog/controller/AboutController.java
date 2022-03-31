package com.lh.myblog.controller;

import com.lh.myblog.bean.User;
import com.lh.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    @Autowired
    private UserService userService;

    @GetMapping("/about")
    public String aboutMe(Model model){
        User me = userService.getById(1);
        model.addAttribute("me",me);
        return "about";
    }
}
