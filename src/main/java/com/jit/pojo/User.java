package com.jit.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: forum
 * @description: User实体类
 * @author: XZQ
 * @create: 2019-11-13 14:59
 **/
@Data
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String email;

    private Date register_time;

    private String description;

    private String wechat;

    private String github;

    private Integer follow;

    private Integer fans;

    private String follow_ids;

    private String fans_ids;

}
