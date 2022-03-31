package com.lh.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.myblog.bean.Friend;
import com.lh.myblog.mapper.FriendMapper;
import com.lh.myblog.service.FriendService;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {
}
