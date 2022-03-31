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
public class Blog {

    private Long id;
    private String title;//标题
    private String content;//内容
    private String flag;//标记，文章来源
    private String firstPicture;//首图地址
    private Integer views;//浏览次数
    private boolean appreciation;//赞赏开启
    private boolean shareStatement;//版权开启
    private boolean commentabled;//评论开启
    private boolean published;//是否发布
    private boolean recommend;//是否推荐
    private Timestamp createTime;//创建时间
    private Timestamp updateTime;//更新时间
    private Long typeId;//分类id
    private Long userId;//用户id
    private String description;//博客描述，用于在前端列表展示

    @TableField(exist = false)
    private Type type;//多对一，多个博客可以一个类型
    @TableField(exist = false)
    private User user;//多对一、
    @TableField(exist = false)
    private List<Comment> comments = new ArrayList<>();//一对多

    public Blog(Long id, String title, String content, String flag, String firstPicture, Integer views, boolean appreciation, boolean shareStatement, boolean commentabled, boolean published, boolean recommend, Timestamp createTime, Timestamp updateTime, Long typeId, Long userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.flag = flag;
        this.firstPicture = firstPicture;
        this.views = views;
        this.appreciation = appreciation;
        this.shareStatement = shareStatement;
        this.commentabled = commentabled;
        this.published = published;
        this.recommend = recommend;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.typeId = typeId;
        this.userId = userId;
    }

    public Blog() {
    }
}
