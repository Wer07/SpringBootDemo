package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wyw on 2018/2/1
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/addUser",method = RequestMethod.PUT)
    public String addUser(@RequestBody User user){
        boolean flag = userService.addUser(user);
        if(flag) {
            return user.toString();
        }else{
            return "addUserFail";
        }
    };
}
