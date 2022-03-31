package com.lh.myblog.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.myblog.bean.Diary;
import com.lh.myblog.bean.User;
import com.lh.myblog.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminDiaryController {

    @Autowired
    private DiaryService diaryService;

    //日记新增页面
    @GetMapping("/diary/input")
    public String addDiary(){
        return "admin/diarys-input";
    }

    //日记新增提交
    @PostMapping("/diary")
    public String addPost(Diary diary){
        diaryService.save(diary);
        return "redirect:/diary";
    }

    //删除日记，此功能链接直接放在前端日记显示页，需登录管理员账号才显示
    @GetMapping("/diary/delete/{id}")
    public String deleteDiary(@PathVariable("id") Long id, HttpSession session){
        //虽然前端是在管理员登录的情况下才显示删除按钮，但还是需要验证是否是管理员登录，防止非管理员直接在url中发起删除指令
        User user = (User) session.getAttribute("user");
        if (user != null && user.getId() == 1) {
            diaryService.removeById(id);
        }
        return "redirect:/diary";
    }

    //编辑日记，此功能链接直接放在前端日记显示页，需登录管理员账号才显示
    @GetMapping("/diary/update/{id}")
    public String updateDiary(@PathVariable("id") Long id,Model model){
        Diary diary = diaryService.getById(id);
        model.addAttribute("diary",diary);
        return "/admin/diarys-input";
    }

    //编辑日记提交
    @PostMapping("/diary/{id}")
    public String updatePost(@PathVariable("id") Long id,Diary diary){
        diary.setId(id);
        diaryService.updateById(diary);
        return "redirect:/diary";
    }
}
