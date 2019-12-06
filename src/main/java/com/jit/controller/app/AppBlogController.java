package com.jit.controller.app;

import com.jit.service.BlogService;
import com.jit.vo.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-12-04 15:09
 **/
@RestController
public class AppBlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/app/blog/{id}")
    public BlogDto getBlog(@PathVariable("id") Integer id) {
        return blogService.findBlogDto(id);
    }

    @GetMapping("/app/blog/randList")
    public List<BlogDto> getBlogList() {
        return blogService.listRandBlogDto();
    }

}
