package com.lh.myblog.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class User {


    private Long id;
    private String nickname;//昵称
    private String username;//用户名
    private String password;//密码
    private String qq;
    private String email;//邮箱
    private Integer type;//是否管理员
    private String avatar;//头像地址
    private Timestamp createTime;//创建时间
    private Timestamp updateTime;//更新时间
    private String info;//一些个人信息
    private String webInfo;//一些网站信息

    @TableField(exist = false)
    private List<Blog> blogs = new ArrayList<>();//一对多

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long id, String nickname, String username, String password, String email, Integer type, String avatar, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
        this.avatar = avatar;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
    }
}
