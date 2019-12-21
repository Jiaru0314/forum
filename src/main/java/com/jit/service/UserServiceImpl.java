package com.jit.service;

import com.jit.mapper.UserMapper;
import com.jit.pojo.User;
import com.jit.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-11-13 15:30
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Integer register(String username, String password) {
        String encodePassword = passwordEncoder.encode(password);
        return userMapper.saveUser(username, encodePassword);
    }

    @Override
    public User login(String username, String password) {
        return userMapper.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public void update(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public User findUserByid(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public List<User> findHotUsers() {
        return userMapper.findHotUsers();
    }

    @Override
    public User checkUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public List<User> findFansList(Integer userId) {
        User user = userMapper.findUserById(userId);
        List<Integer> fansList = ListUtil.stringToList(user.getFans_ids());
        return userMapper.findRelatedUsers(fansList);
    }

    @Override
    public List<User> findFollowsList(Integer userId) {
        User user = userMapper.findUserById(userId);
        List<Integer> followsList = ListUtil.stringToList(user.getFollow_ids());
        return userMapper.findRelatedUsers(followsList);
    }

}
