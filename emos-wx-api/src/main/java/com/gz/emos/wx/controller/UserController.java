package com.gz.emos.wx.controller;

import com.gz.emos.wx.common.R;
import com.gz.emos.wx.common.utils.RedisUtil;
import com.gz.emos.wx.config.shiro.JwtUtil;
import com.gz.emos.wx.service.TbUserService;
import com.gz.emos.wx.domain.vo.LoginFormVO;
import com.gz.emos.wx.domain.vo.RegisterFormVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

/**
 * @author: GZ
 * @date: 2022/10/11 16:38
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户模块")
public class UserController {

    @Autowired
    private TbUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${emos.jwt.cache-expire}")
    private long cacheExpire;

    @PostMapping("/register")
    @Operation(summary = "注册用户")
    public R register(@Valid @RequestBody RegisterFormVO formVO) {
        int userId = userService.registerUser(formVO.getRegisterCode(), formVO.getCode(), formVO.getNickName(), formVO.getPhoto());
        String token = jwtUtil.createToken(userId);
        Set<String> permissions = userService.searchUserPermissions(userId);
        //保存token到缓存
        redisUtil.set(token, userId + "", cacheExpire);
        return R.ok("用户注册成功").put("token", token).put("permission", permissions);
    }

    @PostMapping("/login")
    @Operation(summary = "登录系统")
    public R login(@Valid @RequestBody LoginFormVO formVO){
        int userId= userService.login(formVO.getCode());
        String token = jwtUtil.createToken(userId);
        Set<String> permissions = userService.searchUserPermissions(userId);
        //保存token到缓存
        redisUtil.set(token, userId + "", cacheExpire);
        return R.ok("登录成功").put("token", token).put("permission", permissions);
    }
}
