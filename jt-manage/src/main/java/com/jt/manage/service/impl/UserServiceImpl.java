package com.jt.manage.service.impl;

import com.jt.manage.mapper.UserMapper;
import com.jt.manage.pojo.User;
import com.jt.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
