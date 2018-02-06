package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by wyw on 2018/2/2
 */
@EnableWebSecurity  //通过@EnableWebSecurity注解开启SpringSecurity的功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{ //继承WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //通过重写configure方法添加我们自定义的认证方式

    }

    @Bean
    public MyFilterSecurityInterceptor myFilterSecurityInterceptor() throws Exception{
        MyFilterSecurityInterceptor myFilterSecurityInterceptor = new MyFilterSecurityInterceptor();
        myFilterSecurityInterceptor.setAuthenticationManager(authenticationManager());
        return myFilterSecurityInterceptor;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests() //通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/login", "/home").permitAll()  //指定了/和/home不需要任何认证就可以访问
                .anyRequest().authenticated()  //其他的路径都必须通过身份验证
                .and()
                .addFilter(myFilterSecurityInterceptor());
                /*.and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/index")  //设置默认登录成功跳转页面
                .failureUrl("/login?error")  //设置了登录失败地址
                .permitAll()  //登录页面任何人都可以访问
                .and()
                .rememberMe()   //开启cookie保存用户数据
                .tokenValiditySeconds(60 * 60 * 24 * 7)  //设置cookie有效期
                .key("1234567890")  //设置cookie的私钥
                .and()
                .logout()
                .logoutUrl("/user_logout")  //默认注销行为为logout，可通过该方式来修改
                .logoutSuccessUrl("/logout_success")  //设置注销成功后跳转页面，默认是跳转到登录页面
                .permitAll()*/;  //注销请求也是任何人都可以访问的
    }

    /**
     * 在内存中创建了一个用户，该用户的名称为admin，密码为admin，用户角色为USER
     * @param builder
     * @throws Exception
     */
    /*@Autowired
    public void createUser(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("USER");
    }*/
}
