package com.jit.controller.app;

import com.jit.pojo.Comment;
import com.jit.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-12-04 15:10
 **/
@RestController
@RequestMapping("/app/comment")
public class AppCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    private Integer addComment(@RequestBody Comment comment) {
        comment.setCreateTime(new Date());
        return commentService.saveComment(comment);
    }

}
