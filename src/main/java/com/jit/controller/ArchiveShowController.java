package com.jit.controller;

import com.jit.pojo.User;
import com.jit.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @program: forum
 * @description: 获取当前认证用户的ID 通过ID查询其所有Blog并返回给archive页面
 * @author: XZQ
 * @create: 2019-11-20 09:22
 **/
@Controller
public class ArchiveShowController {

    private static final String ARCHIVES = "archives";

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String toArchives(Model model, HttpSession session) {
        User current_user = (User) session.getAttribute("user");
        Integer current_id = current_user.getId();
        model.addAttribute("archiveMap", blogService.archiveBlog(current_id));
        model.addAttribute("blogCount", blogService.countBlog(current_id));
        return ARCHIVES;
    }
}
