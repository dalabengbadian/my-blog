package com.lh.myblog.controller;

import com.lh.myblog.bean.Diary;
import com.lh.myblog.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DiaryController {

    @Autowired
    private DiaryService diaryService;

    //日记页面，按年份和时间倒序展示日记
    @GetMapping("/diary")
    public String diary(Model model){
        //获取当前系统时间年份
        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        String s = yyyy.format(new Date());
        int year = Integer.parseInt(s);
        //存放按年份分组的日记集合
        ArrayList<List<Diary>> yearDiarys = new ArrayList<>();
        //从当前年份倒序遍历到2022年,按年份查询日记，放进上面的集合中
        for (int i = year; i >= 2022; i--) {
            yearDiarys.add(diaryService.listByYear(i));
        }
        model.addAttribute("yearDiarys", yearDiarys);
        return "diary";
    }
}
