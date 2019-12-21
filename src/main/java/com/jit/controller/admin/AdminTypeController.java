package com.jit.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.pojo.Type;
import com.jit.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-12-10 20:17
 **/
@Controller
@RequestMapping("/admin")
public class AdminTypeController {

    private final static String ADMIN_MANAGE_TYPE = "admin/types";
    private final static String ADMIN_INPUT_TYPE = "admin/types-input";

    @Autowired
    private TypeService typeService;

    /*Type列表*/
    @GetMapping("/types")
    public String manageType(Model model, @RequestParam(defaultValue = "1", required = true, value = "page") Integer page) {
        PageHelper.startPage(page, 3);
        List<Type> types = typeService.findAllType();
        PageInfo<Type> pageInfo = new PageInfo<>(types);
        model.addAttribute("types", types);
        model.addAttribute("page", pageInfo);
        return ADMIN_MANAGE_TYPE;
    }

    /*Type新增页面*/
    @GetMapping("/type/input")
    public String toInputType(Model model) {
        model.addAttribute("type", new Type());
        return ADMIN_INPUT_TYPE;
    }


    /*修改Type界面*/
    @GetMapping("/type/{id}/input")
    public String editType(Model model, @PathVariable Integer id) {
        model.addAttribute("type", typeService.findTypeByTypeId(id));
        return ADMIN_INPUT_TYPE;
    }


    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes) {
        Type type1 = typeService.findTypeByTypeName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "admin/types-input";
        }
        Integer state = typeService.saveType(type.getName());
        if (state != 1) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(Type type, @PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.findTypeByTypeName(type.getName());
        if (type1 != null) {
            attributes.addFlashAttribute("message", "不能添加重复的分类");
            return "admin/types-input";
        }
        Integer state = typeService.updateType(type);
        if (state == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

   /* @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes) {
        typeService.deleteTypeById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }*/

}
