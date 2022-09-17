package com.gz.emos.wx.controller;

import com.gz.emos.wx.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: GZ
 * @date: 2022/9/8 17:36
 */
@Api(tags = {"测试web接口"})
@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/hello")
    @ApiOperation(value = "测试web接口", notes = "hello")
    public R sayHello() {
        return R.ok().put("message","HelloWorld");
    }
}
