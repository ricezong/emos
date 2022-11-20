package com.gz.emos.wx.controller;

import cn.hutool.core.date.DateUtil;
import com.gz.emos.wx.common.R;
import com.gz.emos.wx.config.shiro.JwtUtil;
import com.gz.emos.wx.service.TbCheckinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: GZ
 * @date: 2022/11/18 9:40
 */
@RequestMapping("/checkin")
@RestController
@Slf4j
@Tag(name = "签到模块")
public class CheckinController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TbCheckinService checkinService;

    @GetMapping("/validCanCheckIn")
    @Operation(summary = "查看用户今天是否可以签到")
    public R validCanCheckIn(@RequestHeader("token") String token) {//@RequestHeader注解可以从请求头提取数据
        int userId = jwtUtil.getUserId(token);
        String result = checkinService.validCanCheckIn(userId, DateUtil.today());
        return R.ok(result);
    }
}
