package com.lh.myblog.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author lh
 * @create 2022-03-30 17:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QQInfo {

    private String avatar;
    private String qq;
    private String nickname;
    private String email;
}
