package com.jit.service;

import com.jit.mapper.UserMapper;
import com.jit.pojo.User;
import org.apache.ibatis.annotations.Mapper;
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
    public void register(String username, String password) {
        String encodePassword = passwordEncoder.encode(password);
        userMapper.saveUser(username, encodePassword);
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
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }

    @Override
    public User checkUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
