package com.jit.controller.app;

import com.jit.pojo.Blog;
import com.jit.pojo.User;
import com.jit.service.BlogService;
import com.jit.service.UserService;
import com.jit.vo.BlogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.ElementType;
import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-12-03 16:03
 **/
@RestController
public class AppUserController {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("/app/user/{id}")
    public User get(@PathVariable("id") Integer id) {
        return userService.findUserByid(id);
    }

    @GetMapping("/app/user/list")
    public List<User> list() {
        return userService.findAllUser();
    }

    @PostMapping("/app/login")
    public Integer login(@RequestBody User user) {
        User dataUser = userService.login(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        if (null == dataUser) {
            return null;
        } else {
            return dataUser.getId();
        }
    }
}
