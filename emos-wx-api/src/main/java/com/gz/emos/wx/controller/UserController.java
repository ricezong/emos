package com.gz.emos.wx.controller;

import com.gz.emos.wx.common.R;
import com.gz.emos.wx.common.utils.RedisUtil;
import com.gz.emos.wx.config.shiro.JwtUtil;
import com.gz.emos.wx.service.TbUserService;
import com.gz.emos.wx.vo.RegisterFormVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"用户模块Web接口"})
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
    @ApiOperation("注册用户")
    public R register(@Valid @RequestBody RegisterFormVO formVO) {
        int userId = userService.registerUser(formVO.getRegisterCode(), formVO.getCode(), formVO.getNickName(), formVO.getPhoto());
        String token = jwtUtil.createToken(userId);
        Set<String> permissions = userService.searchUserPermissions(userId);
        //保存token到缓存
        redisUtil.set(token, userId + "", cacheExpire);
        return R.ok("用户注册成功").put("token", token).put("permission", permissions);
    }

}
