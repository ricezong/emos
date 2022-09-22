package com.gz.emos.wx.config.shiro;

import org.springframework.stereotype.Component;

/**
 * 之所以要把 新令牌 保存到 ThreadLocalToken 里面，是因为要向 AOP切面类 传递这个 新令牌
 * 只要是同一个线程，往 ThreadLocal 里面写入数据和读取数据是
 * 完全相同的。在Web项目中，从 OAuth2Filter 到 AOP切面类 ，都是由同一个线程来执行的，中
 * 途不会更换线程。所以我们可以放心的把新令牌保存都在 ThreadLocal 里面， AOP切面类 可以成
 * 功的取出新令牌，然后往 R对象 里面添加新令牌即可
 */

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
