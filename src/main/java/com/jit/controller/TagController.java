package com.jit.controller;

import com.jit.pojo.Tag;
import com.jit.service.BlogService;
import com.jit.service.TagService;
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
@RequestMapping("/tag")
public class TagController {

    private static final String TAGS = "tags";
    private static final String SEARCH = "search";

    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags")
    public String toTagPage(Model model) {
        model.addAttribute("tags", tagService.findAllTag());
        return TAGS;
    }

    @GetMapping("/{id}")
    public String toTagPage(Model model, @PathVariable Integer id) {
        List<BlogDto> blogDtoList = blogService.findBlogDtoByTagId(id);
        model.addAttribute("blogs", blogDtoList);
        model.addAttribute("blogCounts", blogDtoList.size());
        return SEARCH;
    }
}
