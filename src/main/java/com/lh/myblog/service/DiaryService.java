package com.lh.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.myblog.bean.Diary;

import java.util.List;

public interface DiaryService extends IService<Diary> {

    List<Diary> listByYear(Integer year);
}
