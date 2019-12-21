package com.jit.controller.app;

import com.jit.pojo.Blog;
import com.jit.pojo.Comment;
import com.jit.service.BlogService;
import com.jit.service.CommentService;
import com.jit.vo.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-12-04 15:09
 **/
@RestController
@RequestMapping("/app/blog")
public class AppBlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public BlogDto getBlog(@PathVariable("id") Integer id) {
        List<Comment> comments = commentService.listComment(id);
        BlogDto blogDto = blogService.findBlogDto(id);
        blogDto.setComments(comments);
        return blogDto;
    }

    /*查询推荐的Blog*/
    @GetMapping("/randList")
    public List<BlogDto> getBlogList() {
        return blogService.listRandBlogDto();
    }

    /*新建Blog*/
    @PostMapping
    public Integer addBlog(@RequestBody Blog blog) {
        blog.setCreate_time(new Date());
        return blogService.saveBlog(blog);
    }

    /*修改Blog*/
    @PutMapping
    public Integer updateBlog(@RequestBody Blog blog) {
        return blogService.updateBlog(blog);
    }

    @DeleteMapping("/{id}")
    public Integer deleteBlog(@PathVariable("id") Integer blogId) {
        return blogService.deleteBlogById(blogId);
    }

    @GetMapping("/user/{id}")
    public List<BlogDto> findUserBlogs(@PathVariable("id") Integer userId) {
        return blogService.findBlogByUserId(userId);
    }

    /*根据搜索结果查询相关Blog*/
    @GetMapping("/search")
    public List<BlogDto> search(@RequestParam("search") String searchParam) {
        return blogService.findBlogDtoBySearchParam(searchParam);
    }

    /*通过userId查询其关注用户的Blog*/
    @GetMapping("/followBlogList/{id}")
    public List<BlogDto> getFollowBlogList(@PathVariable("id") Integer userId) {
        return blogService.findRecommendBlogs(userId);
    }

    /*查询最新的Blog*/
    @GetMapping("/newestBlogList")
    public List<BlogDto> getNewestBlogList() {
        return blogService.findNewestBlogs(10);
    }

}
