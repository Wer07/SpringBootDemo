package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleMapper;
import com.example.demo.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyw on 2018/2/2
 */
//MyUserDetailService这个类负责的是只是获取登陆用户的详细信息（包括密码、角色等），不负责和前端传过来的密码对比，只需返回User对象，后会有其他类根据User对象对比密码的正确性（框架帮我们做）
@Service
public class MyUserDetailService implements UserDetailsService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    //之所以要实现UserDetailsService接口，是因为在SpringSecurity中我们配置相关参数需要UserDetailsService类型的数据
    //登陆验证时，通过username获取用户的所有权限信息，
    //并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userMapper.selectByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username + " do not exist!");
        } else {
            List<Role> roles = this.roleMapper.selectByUid(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            //写入用户的角色  ***  切记 由于框架原因 角色名称要以 ROLE_ 开头 ***
            //源码：org.springframework.security.access.expression.SecurityExpressionRoot hasAnyRole()
            for (Role role : roles) {
                if (role != null && role.getRoleName() != null) {
                    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleCode());
                    //此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantedAuthorities);
            return userDetails;
        }
    }
}
