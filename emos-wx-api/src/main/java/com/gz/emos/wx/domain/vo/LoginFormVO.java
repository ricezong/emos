package com.gz.emos.wx.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: GZ
 * @date: 2022/10/24 16:26
 */
@Schema(description = "登录表单信息")
@Data
public class LoginFormVO {

    @Schema(description = "微信临时授权码")
    @NotBlank(message = "微信临时授权码不能为空")
    private String code;
}
