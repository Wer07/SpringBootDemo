package com.example.demo.config;

import com.example.demo.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by wyw on 2018/2/5
 */

/**
 * 类MyAuthenticationProvider 自定义的用户名密码验证
 */
public class MyAuthenticationProvider implements AuthenticationManager,AuthenticationProvider {
    @Autowired
    private MyUserDetailService myUserDetailService;
//    注：authenticate()方法
//    获取过滤器封装的token信息
//    调取UserDetailsService获取用户信息（数据库认证）->判断通过与否
//    通过则封装一个新的AuthenticationToken，并返回
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("用户没有找到");
        }
        //加密过程这里体现
        if(!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("密码错误");
        }
        Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userDetails,password,grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
