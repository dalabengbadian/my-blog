package com.lh.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.myblog.bean.Message;
import com.lh.myblog.mapper.MessageMapper;
import com.lh.myblog.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lh
 * @create 2022-03-30 23:09
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    /**
     * 留言展示功能，需要将多级父子关系转换为二级关系
     * 查出所有留言，然后调用下面的方法，得到最顶级的父留言及它们内部封装的子留言
     * @param 
     * @return
     */
    @Override
    public List<Message> listAllMessage() {
        QueryWrapper<Message> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Message> messages = list(wrapper);
        List<Message> messages1 = addReplyMessages(messages);
        return messages1;
    }

    /**
     * 封装所有message的parentMessage和replyMessages（这一步也可以在数据库交互时完成，如mybatis的映射mapper文件中使用resultMap）
     * @param messages
     * @return
     */
    public List<Message> addReplyMessages(List<Message> messages){
        //新增一个集合，只存放顶级父留言
        ArrayList<Message> messages1 = new ArrayList<>();
        for (Message message : messages) {
            //筛选有父留言的留言
            if (message.getParentMessageId() != null){
                for (Message message1 : messages) {
                    if (message1.getId() == message.getParentMessageId()){
                        message.setParentMessage(message1);
                        message1.getReplyMessages().add(message);
                        break;
                    }
                }
            }else {
                messages1.add(message);
            }
        }
        eachMessages(messages1);
        return messages1;
    }

    //临时集合，用于临时保存需要封装到最顶级父留言的留言
    private List<Message> tempMessages = new ArrayList<>();

    /**
     * 遍历所有顶级父留言，获取他们的子留言集合，调用递归方法，一级一级的将所有子留言封装进临时集合，最后再赋值给顶级父留言
     * @param messages
     */
    public void eachMessages(List<Message> messages){
        for (Message message : messages) {
            List<Message> replyMessages = message.getReplyMessages();
            recursively(replyMessages);
            message.setReplyMessages(tempMessages);
            //清空临时集合
            tempMessages = new ArrayList<>();
        }
    }

    /**
     * 递归调用，一层一层的遍历每一个子留言的子留言集合，并放入临时集合中。
     * @param replyMessages
     */
    public void recursively(List<Message> replyMessages){
        for (Message replyMessage : replyMessages) {
            tempMessages.add(replyMessage);
            if (replyMessage.getReplyMessages().size() > 0) {
                List<Message> replyMessages1 = replyMessage.getReplyMessages();
                recursively(replyMessages1);
            }
        }
    }
}
