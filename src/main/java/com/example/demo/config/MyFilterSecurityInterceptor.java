package com.example.demo.config;


import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by wyw on 2018/2/5
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //登陆后，每次访问资源都通过这个拦截器拦截
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //filterInvocation里面有一个被拦截的url
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest,servletResponse,filterChain);
        //beforeInvocation这个方法：
        //1.它首先会调用MyInvocationSecurityMetadataSource类的getAttributes方法获取被拦截url所需的权限
        //2.再调用MyAccessDecisionManager类decide方法判断用户是否够权限
        InterceptorStatusToken interceptorStatusToken = super.beforeInvocation(filterInvocation);
        try{
            //3.弄完这一切就会执行下一个拦截器
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(),filterInvocation.getResponse());
        }finally {
            super.afterInvocation(interceptorStatusToken,null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return null;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return null;
    }
}
