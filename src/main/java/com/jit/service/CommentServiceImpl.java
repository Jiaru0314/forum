package com.jit.service;

import com.jit.mapper.CommentMapper;
import com.jit.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-25 13:19
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Integer saveComment(Comment comment) {
        return commentMapper.saveComment(comment);
    }

    @Override
    public List<Comment> listComment(Integer blog_id) {
        return commentMapper.getCommentsByBlogId(blog_id);
    }

}
