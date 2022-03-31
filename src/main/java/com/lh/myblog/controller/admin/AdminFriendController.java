package com.lh.myblog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.myblog.bean.Friend;
import com.lh.myblog.service.FriendService;
import com.lh.myblog.service.impl.FriendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminFriendController {

    @Autowired
    private FriendService friendService;

    //友链列表
    @GetMapping("/friends")
    public String friends(@RequestParam(defaultValue = "1") Integer pageNumber, Model model){
        Page<Friend> page = new Page<>(pageNumber, 5);
        friendService.page(page);
        model.addAttribute("page",page);
        return "admin/friends";
    }

    //友链翻页
    @PostMapping("/friends/page")
    public String friendsPage(@RequestParam(defaultValue = "1") Integer pageNumber, Model model){
        Page<Friend> page = new Page<>(pageNumber, 5);
        friendService.page(page);
        model.addAttribute("page",page);
        return "admin/friends :: friendsList";
    }

    //跳转友链新增页
    @GetMapping("/friends/input")
    public String addFriend(){
        return "admin/friends-input";
    }

    //友链新增提交
    @PostMapping("/friends")
    public String addPost(Friend friend, RedirectAttributes attributes){
        //查询该友链地址是否已存在
        QueryWrapper<Friend> wrapper = new QueryWrapper<>();
        wrapper.eq("url",friend.getUrl());
        Friend one = friendService.getOne(wrapper);

        if (one != null){
            attributes.addFlashAttribute("message","添加失败，该友链地址已存在");
        }else {
            boolean save = friendService.save(friend);
            if (save){
                attributes.addFlashAttribute("message","添加成功");
            }else {
                attributes.addFlashAttribute("message","添加失败");
            }
        }
        return "redirect:/admin/friends";
    }

    //删除友链
    @GetMapping("/friends/{id}/delete")
    public String deleteFriend(@PathVariable("id") Long id,RedirectAttributes attributes){
        boolean remove = friendService.removeById(id);
        if (remove){
            attributes.addFlashAttribute("message","删除成功");
        }else {
            attributes.addFlashAttribute("message","删除失败");
        }
        return "redirect:/admin/friends";
    }
}
