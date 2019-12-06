package com.jit.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: forum
 * @description: Comment 实体类
 * @author: XZQ
 * @create: 2019-11-15 15:45
 **/
@Data
@NoArgsConstructor
public class Comment {

    private Integer id;

    private Integer user_id;

    private String content;

    private Date createTime;

    private Integer blog_id;

    private Integer parent_id;

    private String username;

    private String avatar;

    private List<Comment> replyComments;

    public Comment(Integer user_id, String content, Date createTime, Integer blog_id) {
        this.user_id = user_id;
        this.content = content;
        this.createTime = createTime;
        this.blog_id = blog_id;
    }
}
