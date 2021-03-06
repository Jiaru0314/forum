package com.jit.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.pojo.Type;
import com.jit.pojo.User;
import com.jit.service.BlogService;
import com.jit.service.TagService;
import com.jit.service.TypeService;
import com.jit.service.UserService;
import com.jit.vo.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.rmi.runtime.NewThreadAction;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-15 16:15
 **/
@Controller
public class IndexController {

    private static final String INDEX = "index";
    private static final String RECOMMEND = "recommend";
    private static final String LOGIN = "login";
    private static final String REGISTER = "register";
    private static final String MANAGE_BLOG = "manage-blog";
    private static final String USERS = "users";
    private static final String SEARCH = "search";
    private static final String TOOLS = "tools";
    private static final String REDIRECT_LOGIN = "redirect:/login";
    private static final String REDIRECT_REGISTER = "redirect:/register";

    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String toIndex(Model model) {
        /**
         * 每页显示记录数 @RequestParam(defaultValue = "1", value = "page") Integer page
         */
//        Integer pageSize = 8;
        //分页查询
//        PageHelper.startPage(page, pageSize);
//        List<BlogDto> blogDtoList = blogService.findHotestBlogs();
//        PageInfo<BlogDto> pageInfo = new PageInfo<>(blogDtoList);
//        model.addAttribute("page", pageInfo);

        long startTime = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(4);

        threadPool.execute(() -> {
            model.addAttribute("types", typeService.findHottestType(6));
            countDownLatch.countDown();
        });
        threadPool.execute(() -> {
            model.addAttribute("tags", tagService.findHottestTag(6));
            countDownLatch.countDown();
        });
        threadPool.execute(() -> {
            model.addAttribute("blogs", blogService.findHotestBlogs());
            countDownLatch.countDown();
        });
        threadPool.execute(() -> {
            model.addAttribute("newBlogs", blogService.findNewestBlogs(8));
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("操作耗时\t" + (System.currentTimeMillis() - startTime) + " ms");
        return INDEX;
    }

    @GetMapping("/recommend")
    public String toRecommend(Model model, HttpSession session) {
        User current_user = (User) session.getAttribute("user");
        Integer current_id = current_user.getId();
        model.addAttribute("blogs", blogService.findRecommendBlogs(current_id));
        return RECOMMEND;
    }

    @GetMapping("/login")
    public String toLogin() {
        return LOGIN;
    }

    @GetMapping("/register")
    public String toRegister() {
        return REGISTER;
    }

    @GetMapping("/manage")
    public String toManage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("blogs", blogService.findBlogByUserId(user.getId()));
        return MANAGE_BLOG;
    }

    @GetMapping("/hotUser")
    public String toHotUser(Model model, HttpSession session) {
        User current_user = (User) session.getAttribute("user");
        List<User> users = userService.findHotUsers();
        if (current_user == null) {
            //如果用户未登录 显示所有的用户
            model.addAttribute("users", users);
        } else {
            //如果用户已登录 去除其本身
            Integer current_id = current_user.getId();
            Iterator<User> iterator = users.iterator();
            while ((iterator.hasNext())) {
                User user = iterator.next();
                if (user.getId() == current_id) {
                    iterator.remove();
                    break;
                }
            }
            model.addAttribute("users", users);
        }
        return USERS;
    }

    @PostMapping("/search")
    public String toSearch(@RequestParam String query, Model model) {
        List<BlogDto> blogs = blogService.findBlogDtoBySearchParam(query);
        model.addAttribute("blogs", blogs);
        model.addAttribute("blogCounts", blogs.size());
        return SEARCH;
    }

    @GetMapping("/tools")
    public String toTools() {
        return TOOLS;
    }

    /**
     * 处理注册
     */
    @PostMapping("register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           RedirectAttributes attributes) {
        System.out.println("进入注册");
        User user = userService.checkUsername(username);
        //如果用户名存在
        if (user != null) {
            attributes.addFlashAttribute("message", "用户名已存在,请换一个");
            return REDIRECT_REGISTER;
        } else {
            //用户名不存在 注册用户
            System.out.println("1");
            Integer value = userService.register(username, password);
            System.out.println("2");
            attributes.addFlashAttribute("message", "注册成功，请登录");
            return REDIRECT_LOGIN;
        }
    }
}
