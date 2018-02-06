package com.example.demo.controller;

import com.example.demo.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wyw on 2018/2/2
 */
@RestController
public class LoginController {
    @Autowired
    private MyUserDetailService myUserDetailService;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,@RequestParam("roleId") Long roleId){
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username1 = ((UserDetails)principal).getUsername();
        } else {
            String username1 = principal.toString();
        }*/
        UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
        if(userDetails.getPassword().equals(password)){
            return "pwd right";
        }
        return "pwd wrong";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/aaa")
    public String aaa(){
        return "aaa";
    }
}
