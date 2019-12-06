package com.jit.controller;

import com.jit.pojo.Type;
import com.jit.service.BlogService;
import com.jit.service.TypeService;
import com.jit.vo.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-14 12:29
 **/
@Controller
@RequestMapping("/type")
public class TypeController {

    private static final String TYPES = "types";
    private static final String SEARCH = "search";

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types")
    public String toTypePage(Model model) {
        List<Type> types = typeService.findAllType();
        model.addAttribute("types", types);
        return TYPES;
    }

    @GetMapping("/{id}")
    public String toType(Model model, @PathVariable("id") Integer id) {
        List<BlogDto> blogs = blogService.findBlogDtoByTypeId(id);
        int blogcounts = blogs.size();
        model.addAttribute("blogs", blogs);
        model.addAttribute("blogCounts", blogcounts);
        return SEARCH;
    }
}
