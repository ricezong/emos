package com.gz.emos.wx.aop;

import com.gz.emos.wx.common.R;
import com.gz.emos.wx.config.shiro.ThreadLocalToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AOP切面类 ，拦截所有Web方法返回的 R对象,
 * 然后在 R对象 里面添加 新令牌
 * @author: GZ
 * @date: 2022/9/17 14:11
 */
@Aspect
@Component
public class TokenAspect {
    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Pointcut("execution(public * com.gz.emos.wx.controller.*.*(..)))")
    public void aspect() {
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        R r = (R) point.proceed(); //方法执行结果
        String token = threadLocalToken.getToken();
        //如果ThreadLocal中存在Token，说明是更新的Token
        if (token != null) {
            r.put("token", token); //往响应中放置Token
            threadLocalToken.clear();
        }
        return r;
    }
}
