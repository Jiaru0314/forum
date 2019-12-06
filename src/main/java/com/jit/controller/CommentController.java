package com.jit.controller;

import com.jit.pojo.Comment;
import com.jit.pojo.User;
import com.jit.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-25 19:12
 **/
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public String post(Integer blog_id, String content, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer user_id = user.getId();
        Comment comment = new Comment(user_id, content, new Date(), blog_id);
        commentService.saveComment(comment);
        return "redirect:/blog/" + blog_id;
    }
}
