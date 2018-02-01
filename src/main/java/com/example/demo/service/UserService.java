package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wyw on 2018/2/1
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public boolean addUser(User user){
        Long id = System.currentTimeMillis();
        user.setId(id);
        userMapper.save(user);
        return true;
    }
}
