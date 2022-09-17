package com.gz.emos.wx.config.shiro;

import org.springframework.stereotype.Component;


@Component
public class ThreadLocalToken {
    //ThreadLocal ，只要是同一个线程，往 ThreadLocal 里面写入数据和读取数据是完全相同的。
    private ThreadLocal<String> local = new ThreadLocal<String>();

    public void setToken(String token) {
        local.set(token);
    }

    public String getToken() {
        return (String) local.get();
    }

    public void clear() {
        local.remove();
    }
}
