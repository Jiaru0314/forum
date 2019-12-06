package com.jit.controller;

import com.jit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-20 11:59
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;

}
