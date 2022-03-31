package com.lh.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.myblog.bean.User;

import java.util.List;

public interface UserService extends IService<User> {

    //验证后台登录的账号密码
    User checkUser(String username, String password);

}
