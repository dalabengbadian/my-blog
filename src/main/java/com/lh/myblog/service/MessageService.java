package com.lh.myblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.myblog.bean.Message;

import java.util.List;

/**
 * @author lh
 * @create 2022-03-30 23:08
 */
public interface MessageService extends IService<Message> {

    List<Message> listAllMessage();
}
