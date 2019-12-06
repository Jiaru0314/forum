package com.jit.service;

import com.jit.pojo.Comment;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-25 13:13
 **/
public interface CommentService {

    Integer saveComment(Comment comment);

    List<Comment> listComment(Integer blog_id);

}
