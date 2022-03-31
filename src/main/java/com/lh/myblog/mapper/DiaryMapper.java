package com.lh.myblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.myblog.bean.Diary;

import java.util.List;

public interface DiaryMapper extends BaseMapper<Diary> {
    List<Diary> selectByYear(Integer year);
}
