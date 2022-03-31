package com.lh.myblog.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class Message {

    private Long id;
    private String nickname;//昵称
    private String avatar;//头像地址
    private long userId;//用户id
    private String content;//内容
    private Timestamp createTime;//创建时间
    private Long parentMessageId;//父留言id
    private boolean webmaster;//是否是博主

    @TableField(exist = false)
    private List<Message> replyMessages = new ArrayList<>();//一对多
    @TableField(exist = false)
    private Message parentMessage;//多对一

    public Message(long id, String nickname, String avatar, long userId, String content, Timestamp createTime) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.userId = userId;
        this.content = content;
        this.createTime = createTime;
    }

    public Message() {
    }
}
