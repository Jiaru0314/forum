package com.jit.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.pojo.Tag;
import com.jit.pojo.Type;
import com.jit.service.BlogService;
import com.jit.service.TagService;
import com.jit.service.TypeService;
import com.jit.vo.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-20 11:59
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final static String ADMIN_MANAGE_BLOG = "admin/manage-blog";

    @Autowired
    private BlogService blogService;

    /*管理页面*/
    @GetMapping("/manage")
    public String toManage(Model model, @RequestParam(defaultValue = "1", required = true, value = "page") Integer page) {
        Integer pageSize = 8;//每页显示记录数
        PageHelper.startPage(page, pageSize); //分页查询
        List<BlogDto> blogList = blogService.listAllBlog();
        PageInfo<BlogDto> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("blogs", blogList);
        model.addAttribute("page", pageInfo);
        return ADMIN_MANAGE_BLOG;
    }
}
