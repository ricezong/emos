package com.gz.emos.wx.config.shiro;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 拦截Http请求，验证Token
 * 因为在 OAuth2Filter 类中要读写 ThreadLocal 中的数据，所以 OAuth2Filter 类必
 * 须要设置成多例的，否则 ThreadLocal 将无法使用
 * @author: GZ
 * @date: 2022/9/16 15:20
 */
@Component
@Scope("prototype")//多例对象
public class OAuth2Filter extends AuthenticatingFilter {

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 拦截请求之后，用于把令牌字符串封装成令牌对象
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return new OAuth2Token(token);
    }

    /**
     * 该方法用于处理所有应该被Shiro处理的请求
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        //允许跨域请求
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        threadLocalToken.clear();
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken(request);
        if (StrUtil.isBlank(token)) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.getWriter().print("无效的令牌");
            return false;
        }
        try {
            //验证令牌有效性
            jwtUtil.verifierToken(token);
        } catch (TokenExpiredException e) {
            //客户端令牌过期，查询Redis中是否存在令牌，如果存在令牌就重新生成一个令牌给客户端
            if (redisTemplate.hasKey(token)) {
                redisTemplate.delete(token);
                int userId = jwtUtil.getUserId(token);
                token = jwtUtil.createToken(userId);
                //把新的令牌保存到Redis中
                redisTemplate.opsForValue().set(token, String.valueOf(userId), cacheExpire, TimeUnit.DAYS);
                //把新令牌绑定到线程
                threadLocalToken.setToken(token);
            } else {
                //如果Redis不存在令牌，让用户重新登录
                response.setStatus(HttpStatus.SC_UNAUTHORIZED);
                response.getWriter().print("令牌已过期，请重新登录");
                return false;
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            response.getWriter().print("无效的令牌");
            return false;
        }
        //认证授权
        boolean b = executeLogin(servletRequest, servletResponse);
        return b;
    }

    /**
     * 认证失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        try {
            resp.getWriter().print(e.getMessage());
        } catch (IOException exception) {

        }
        return false;
    }

    /**
     * 拦截请求，判断请求是否需要被Shiro处理
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        //AJAX提交application/json数据的时候，会发出Option请求，这里放行Option请求，不需要Shiro处理
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        //除了Option请求，其余请求都需要被Shiro处理
        return false;
    }

    private String getRequestToken(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            token = servletRequest.getParameter("token");
        }
        return token;
    }
}
