package com.jit.controller.app;

import com.jit.pojo.User;
import com.jit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-12-03 16:03
 **/
@RestController
@RequestMapping("/app/user")
public class AppUserController {

    private final static Integer USERNAME_USED = -1;
    BCryptPasswordEncoder encode = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    /*根据userId查询User*/
    @GetMapping("/{id}")
    public User get(@PathVariable("id") Integer userId) {
        return userService.findUserByid(userId);
    }

    /*查看所有用户*/
    @GetMapping("/list")
    public List<User> list() {
        return userService.findHotUsers();
    }

    @PostMapping("/login")
    public Integer login(@RequestBody User user) {
        User dataUser = userService.checkUsername(user.getUsername());
        boolean matches = encode.matches(user.getPassword(), dataUser.getPassword());
        if (matches == false) {
            return null;
        } else {
            return dataUser.getId();
        }
    }

    @PostMapping("/register")
    public Integer register(@RequestBody User user) {
        User checkUser = userService.checkUsername(user.getUsername());
        //如果用户名存在
        if (null != checkUser) {
            return USERNAME_USED;
        }
        //用户名不存在 注册用户
        return userService.register(user.getUsername(), user.getPassword());
    }

    /*通过UserId查询其所有粉丝*/
    @GetMapping("/fans/{id}")
    public List<User> getFansList(@PathVariable("id") Integer id) {
        return userService.findFansList(id);
    }

    /*通过UserId查询其所有关注者*/
    @GetMapping("/follows/{id}")
    public List<User> getFollowsList(@PathVariable("id") Integer id) {
        return userService.findFollowsList(id);
    }
}
