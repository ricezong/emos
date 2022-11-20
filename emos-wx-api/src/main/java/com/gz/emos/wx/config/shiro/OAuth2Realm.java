package com.gz.emos.wx.config.shiro;

import com.gz.emos.wx.domain.TbUser;
import com.gz.emos.wx.service.TbUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 定义认证与授权的实现方法
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TbUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户对象
        TbUser user = (TbUser) principals.getPrimaryPrincipal();
        int id = user.getId();
        //TODO 查询用户的权限列表
        Set<String> permissions = userService.searchUserPermissions(id);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //TODO 把权限列表添加到info对象中
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 认证(验证登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取token
        String accessToken = (String) token.getPrincipal();
        //TODO 从令牌中获取userId，然后检测该账户是否被冻结。
        int userId = jwtUtil.getUserId(accessToken);
        TbUser tbUser = userService.searchById(userId);
        if (tbUser==null){
            throw new LockedAccountException("账户已锁定，请联系管理员");
        }
        //TODO 往info对象中添加用户信息、Token字符串
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(tbUser,accessToken,getName());
        return info;
    }
}
