package com.jit.controller;

import com.jit.pojo.Tag;
import com.jit.pojo.Type;
import com.jit.pojo.User;
import com.jit.service.BlogService;
import com.jit.service.TagService;
import com.jit.service.TypeService;
import com.jit.service.UserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-13 14:13
 **/
@Controller
@RequestMapping("/user")
public class UserController {
    private static final String USER = "user";
    private static final String REDIRECT_HOTUSER = "redirect:/hotUser";
    private static final String EDIT = "edit";

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String user(@PathVariable("id") Integer user_id, Model model) {
        setUserPageInfo(user_id, model);
        return USER;
    }

    private void setUserPageInfo(Integer user_id, Model model) {
        List<Type> types = typeService.findAllTypeByUserId(user_id);
        List<Tag> tags = tagService.findTagsByUserId(user_id);
        User user = userService.findUserByid(user_id);
        model.addAttribute("user", user);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
    }

    @GetMapping()
    public String me(Model model, HttpSession session) {
        User current_user = (User) session.getAttribute("user");
        Integer current_id = current_user.getId();
        setUserPageInfo(current_id, model);
        return USER;
    }

    @GetMapping("/follow/{id}")
    public String follow(@PathVariable("id") Integer id,
                         HttpSession session, RedirectAttributes attributes) {
        //获取当前user
        User this_user = (User) session.getAttribute("user");
        //获取被关注的user
        User be_follow_user = userService.findUserByid(id);

        boolean state = follow(this_user, be_follow_user);
        if (state) {
            attributes.addFlashAttribute("message", "关注成功");
        } else {
            attributes.addFlashAttribute("message", "您已关注此用户");
        }
        return REDIRECT_HOTUSER;
    }


    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.findUserByid(id));
        return EDIT;
    }

    @PostMapping("/edit")
    public String edit(@RequestParam MultipartFile file, User user, Model model, HttpSession session) throws IOException {
        User raw_user = userService.findUserByid(user.getId());
        raw_user.setNickname(user.getNickname());
        raw_user.setDescription(user.getDescription());
        raw_user.setEmail(user.getEmail());
        raw_user.setGithub(user.getGithub());
        if (!file.isEmpty()) {
            //上传文件路径
            String path = "/image/";
            //上传文件名
            String filename = file.getOriginalFilename();
            File file2 = new File(path, filename);
            byte[] bytes = file.getBytes();
            FileUtils.writeByteArrayToFile(file2, bytes);
            raw_user.setAvatar(path + filename);
        }
        userService.update(raw_user);
        session.setAttribute("user", raw_user);
        return "redirect:/user/" + user.getId();
    }

    public boolean follow(User this_user, User be_follow_user) {
        Integer this_id = this_user.getId();
        String fans_ids = be_follow_user.getFans_ids();
        if (fans_ids == null) {
            //判断fans_ids是否为null
            fans_ids = "";
        } else if (!fans_ids.equals("")) {
            //判断是否已经关注
            String[] split = fans_ids.split(",");
            for (int i = 0; i < split.length; i++) {
                if (this_id == Integer.parseInt(split[i])) {
                    return false;
                }
            }
        }
        fans_ids = fans_ids + this_id + ",";
        be_follow_user.setFans_ids(fans_ids);
        be_follow_user.setFans(be_follow_user.getFans() + 1);
        userService.update(be_follow_user);
        //修改当前用户follows_ids
        String follows_ids = this_user.getFollow_ids();
        if (follows_ids == null) {
            follows_ids = "";
        }
        follows_ids = follows_ids + be_follow_user.getId() + ",";
        this_user.setFollow_ids(follows_ids);
        this_user.setFollow(this_user.getFollow() + 1);
        userService.update(this_user);
        return true;
    }

}
