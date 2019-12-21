package com.jit.service;

import com.jit.pojo.User;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-13 15:04
 **/
public interface UserService {

    Integer register(String username, String password);

    User login(String username, String password);

    void update(User user);

    User findUserByid(int id);

    List<User> findHotUsers();

    User checkUsername(String username);

    List<User> findFansList(Integer userId);

    List<User> findFollowsList(Integer userId);

}
