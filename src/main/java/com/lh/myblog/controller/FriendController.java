package com.lh.myblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lh.myblog.bean.Friend;
import com.lh.myblog.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/friend")
    public String friend(Model model){
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Friend> friends = friendService.list(wrapper);
        model.addAttribute("friends",friends);
        return "friend";
    }

}
