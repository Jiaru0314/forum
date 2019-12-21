package com.jit.controller.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.pojo.Tag;
import com.jit.service.TagService;
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
 * @create: 2019-12-10 19:49
 **/
@Controller
@RequestMapping("/admin")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    private final static String ADMIN_INPUT_TAG = "admin/tags-input";
    private final static String ADMIN_MANAGE_TAG = "admin/tags";

    /*Tag列表*/
    @GetMapping("/tags")
    public String manageTag(Model model, @RequestParam(defaultValue = "1", required = true, value = "page") Integer page) {
        PageHelper.startPage(page, 3);
        List<Tag> tags = tagService.findAllTag();
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        model.addAttribute("tags", tags);
        model.addAttribute("page", pageInfo);
        return ADMIN_MANAGE_TAG;
    }

    /*Tag新增页面*/
    @GetMapping("/tag/input")
    public String toInputTag(Model model) {
        model.addAttribute("tag", new Tag());
        return ADMIN_INPUT_TAG;
    }

    /*修改Tag界面*/
    @GetMapping("/tag/{id}/input")
    public String editTag(Model model, @PathVariable Integer id) {
        model.addAttribute("tag", tagService.findTagByTagId(id));
        return ADMIN_INPUT_TAG;
    }

    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes) {
        Tag tag1 = tagService.findTagByTagName(tag.getName());
        if (tag1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "admin/tags-input";
        }
        Integer state = tagService.saveTag(tag.getName());
        if (state != 1) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, @PathVariable Integer id, RedirectAttributes attributes) {
        Tag tag1 = tagService.findTagByTagName(tag.getName());
        if (tag1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "admin/tags-input";
        }
        Integer state = tagService.updateTag(tag);
        if (state == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }
}
