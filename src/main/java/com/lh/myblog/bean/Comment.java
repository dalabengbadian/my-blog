package com.lh.myblog.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class Comment {

    private Long id;
    private String nickname;//昵称
    private String avatar;//头像地址
    private String content;//评论内容
    private Timestamp createTime;//创建时间
    private Long blogId;//博客id
    private Long parentCommentId;//父评论id
    private boolean adminComment;//是否是博主

    @TableField(exist = false)
    private Blog blog;//多对一
    @TableField(exist = false)
    private List<Comment> replyComments = new ArrayList<>();//一对多，一条回复下可以有多条子回复
    @TableField(exist = false)
    private Comment parentComment;//多对一

    public Comment(long id, String nickname, String avatar, String content, Timestamp createTime) {
        this.id = id;
        this.nickname = nickname;
        this.avatar = avatar;
        this.content = content;
        this.createTime = createTime;
    }

    public Comment() {
    }
}
