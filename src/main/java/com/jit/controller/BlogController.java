package com.jit.controller;

import com.jit.pojo.Blog;
import com.jit.pojo.Type;
import com.jit.pojo.User;
import com.jit.service.BlogService;
import com.jit.service.CommentService;
import com.jit.service.TagService;
import com.jit.service.TypeService;
import com.jit.util.MarkdownUtils;
import com.jit.vo.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-13 21:33
 **/
@Controller
@RequestMapping("/blog")
public class BlogController {

    private static final String BLOGPAGE = "blog";
    private static final String BLOGS_INPUT = "blogs-input";
    private static final String REDIRECT_MANAGE = "redirect:/manage";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public String blog(@PathVariable Integer id, Model model) {
        BlogDto blogDto = blogService.findBlogDto(id);
        String raw_content = blogDto.getContent();
        //将MarkDown格式转化成HTMl格式返回
        blogDto.setContent(MarkdownUtils.markdownToHtml(raw_content));
        model.addAttribute("blog", blogDto);
        model.addAttribute("comments", commentService.listComment(id));
        Blog blog = blogService.getBlogById(id);
        blog.setViews(blog.getViews() + 1);
        blogService.updateBlog(blog);
        return BLOGPAGE;
    }

    /**
     * 去添加博客页面
     *
     * @param model
     * @return
     */
    @GetMapping("/input")
    public String input(Model model) {
        model.addAttribute("tags", tagService.findAllTag());
        model.addAttribute("types", typeService.findAllType());
        model.addAttribute("blog", new Blog());
        return BLOGS_INPUT;
    }

    /**
     * 去修改博客页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("tags", tagService.findAllTag());
        model.addAttribute("types", typeService.findAllType());
        model.addAttribute("blog", blogService.getBlogById(id));
        return BLOGS_INPUT;
    }

    /**
     * 删除博客
     *
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes) {
        Integer integer = blogService.deleteBlogById(id);
        if (integer > 0) {
            attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }
        return REDIRECT_MANAGE;
    }

    /**
     * 给博客点赞
     *
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/prefer/{id}")
    public String ajax(@PathVariable Integer id) {
        Blog blog = blogService.getBlogById(id);
        Integer prefers = blog.getPrefers() + 1;
        blog.setPrefers(prefers);
        blogService.updateBlog(blog);
        return "success";
    }

    /**
     * 添加、修改Blog
     *
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/input")
    public String input(Blog blog, RedirectAttributes attributes, HttpSession session) {
        Integer result;//判断是否修改的标志
        User user = (User) session.getAttribute("user");
        blog.setUser_id(user.getId());
        if (blog.getId() == null) {
            //如果是新增Blog
            blog.setCreate_time(new Date());
            result = blogService.saveBlog(blog);
            //修改type
            updateType(blog.getType_id(), 1);
        } else {
            //如果修改Blog
            int blog_id = blog.getId();
            Blog raw_blog = blogService.getBlogById(blog_id);
            if (raw_blog.getType_id() != blog.getType_id()) {
                //如果type被修改
                updateType(blog.getType_id(), 1);
                //原先typeCounts+1
                updateType(raw_blog.getType_id(), -1);
            }
            result = blogService.updateBlog(blog);
        }

        if (result == 0) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_MANAGE;
    }

    private void updateType(Integer type_id, Integer count) {
        Type type = typeService.findTypeByTypeId(type_id);
        type.setCounts(type.getCounts() + count);
        typeService.updateType(type);
    }
}
