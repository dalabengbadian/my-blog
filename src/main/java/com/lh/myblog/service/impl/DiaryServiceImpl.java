package com.lh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.myblog.bean.Diary;
import com.lh.myblog.mapper.DiaryMapper;
import com.lh.myblog.service.DiaryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaryServiceImpl extends ServiceImpl<DiaryMapper, Diary> implements DiaryService {


    @Override
    public List<Diary> listByYear(Integer year) {
        return baseMapper.selectByYear(year);

    }
}
