package com.lh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.myblog.bean.Type;
import com.lh.myblog.mapper.TypeMapper;
import com.lh.myblog.service.TypeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Override
    public boolean getByName(String name) {

        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        Type type = getOne(wrapper);
        if (type == null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public List<Type> listAllTypeAndCount() {
        return baseMapper.selectAllTypeAndCount();
    }
}
