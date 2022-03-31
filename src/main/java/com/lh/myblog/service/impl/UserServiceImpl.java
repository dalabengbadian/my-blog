package com.lh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.myblog.bean.User;
import com.lh.myblog.mapper.UserMapper;
import com.lh.myblog.service.UserService;
import com.lh.myblog.util.MD5Utils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Override
    public User checkUser(String username, String password) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username)
                .eq("password",MD5Utils.code(password)); //MD5加密
        User user = getOne(wrapper);
        return user;
    }
}
