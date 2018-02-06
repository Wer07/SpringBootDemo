package com.example.demo.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by wyw on 2018/2/5
 */
public class MyAccessDecisionManager implements AccessDecisionManager{
    //检查用户是否够权限访问资源
    //参数authentication是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
    //参数object是url
    //参数configAttributes所需的权限
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //看需求，需要什么策略，可以自己写其中的策略逻辑。通过就返回，不通过抛异常就行了，spring security会自动跳到权限不足页面
        //没有对应的权限的，可以访问
        if(configAttributes == null){
            return;
        }
        //具有其中一个或多个以上的权限的,可以访问
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while(iterator.hasNext()){
            ConfigAttribute configAttribute = iterator.next();
            String needRole = ((SecurityConfig)configAttribute).getAttribute();
            for(GrantedAuthority grantedAuthority : authentication.getAuthorities()){
                if(needRole.equals(grantedAuthority.getAuthority())){
                    return;
                }
            }
        }
        //注意：执行这里，后台是会抛异常的，但是界面会跳转到所配的access-denied-page页面
        throw new AccessDeniedException("no right");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
