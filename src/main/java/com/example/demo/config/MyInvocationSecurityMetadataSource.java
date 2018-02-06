package com.example.demo.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wyw on 2018/2/6
 */
// Spring Security3.0升级至3.2.4版本注意事项
// 配置Spring Security实现用户-角色-权限-资源四层管理，从3.0版本，升级到3.2.4版本时需要注意如下：
// 1、配置文件修改，样式改为http://www.springframework.org/schema/security/spring-security-3.2.xsd
// 2、代码修改，在3.2.4版本中没有UrlMatcher和AntUrlPathMatcher，需要使用RequestMatcher和AntPathRequestMatcher作为替代。
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
    private static Map<String,Collection<ConfigAttribute>> resourceMap = null;
    private RequestMatcher requestMatcher = null;

    //当接收到一个http请求时, filterSecurityInterceptor会调用的方法. 参数object是一个包含url信息的HttpServletRequest实例. 这个方法要返回请求该url所需要的所有权限集合
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(object instanceof FilterInvocation) {
            HttpServletRequest httpServletRequest = ((FilterInvocation) object).getHttpRequest();
            Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
            //比较请求url和资源库url，匹配则赋予对应的角色权限
            if(resourceMap != null && !resourceMap.isEmpty()) {
                Iterator<String> iterator = resourceMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String requestUrl = iterator.next();
                    requestMatcher = new AntPathRequestMatcher(requestUrl);
                    if (requestMatcher.matches(httpServletRequest)) {
                        configAttributes.addAll(resourceMap.get(requestUrl));
                    }
                }
                if(configAttributes.isEmpty()){
                    configAttributes.add(new SecurityConfig("nobody"));
                }
                for(ConfigAttribute configAttribute : configAttributes){
                    System.out.println("请求url匹配角色：" + configAttribute.toString());
                }
                return configAttributes;
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
