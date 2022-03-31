package com.lh.myblog.controller;

import com.lh.myblog.bean.Message;
import com.lh.myblog.bean.QQInfo;
import com.lh.myblog.bean.User;
import com.lh.myblog.service.BlogService;
import com.lh.myblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private BlogService blogService;


    //在配置文件中指定的默认头像路径
    @Value("${comment.avatar}")
    private String avatar;
    
    //跳转留言页面，展示所有留言
    @GetMapping("/message")
    public String message( Model model){
        List<Message> messages = messageService.listAllMessage();
        model.addAttribute("messages",messages);
        return "message";
    }

    //保存留言后异步刷新留言
    @GetMapping("/message/save")
    public String messageSave( Model model){
        List<Message> messages = messageService.listAllMessage();
        model.addAttribute("messages",messages);
        return "message :: messageList";
    }

    //保存留言
    @PostMapping("/messages")
    public String post(Message message, HttpSession session){
        //如果当前session中有管理员登录信息，则将此留言设置站长标记
        User user = (User) session.getAttribute("user");
        if (user != null){
            message.setWebmaster(true);
        }
        //父留言id前端默认赋值为-1,表示没有父留言，但是如果此留言是点击回复的其他留言，则会被赋值为该留言的id
        Long parentMessageId = message.getParentMessageId();
        if (parentMessageId == -1){
            message.setParentMessageId(null);
        }
        //如果该留言用户没有昵称，设置默认昵称
        String nickname = message.getNickname();
        if (nickname == null || nickname ==""){
            message.setNickname("无名人士");
        }
        //如果该留言用户没有头像，设置默认头像
        String avatar1 = message.getAvatar();
        if (avatar1 == null || avatar1 == ""){
            message.setAvatar(avatar);
        }
        messageService.save(message);
        return "redirect:/message/save";
    }

    //删除留言
    @GetMapping("/messages/delete/{messageId}")
    public String deleteMessages(@PathVariable("messageId") Long messageId,HttpSession session){
        //验证是否已登录管理员账号
        User user = (User) session.getAttribute("user");
        if (user != null && user.getId() ==1){
            messageService.removeById(messageId);
        }
        return "redirect:/message";
    }


    //根据qq获取头像、昵称等
    @PostMapping("/message/qqinfo")
    public String getQQInfo(String qq,Model model){
        if (qq == null || qq.trim() ==""){
            model.addAttribute("qqInfo",null);
        }else {
            QQInfo qqInfo = blogService.getQQInfo(qq);
            model.addAttribute("qqInfo",qqInfo);
        }
        return "message :: qqInfo";
    }

}
