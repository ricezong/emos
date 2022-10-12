package com.gz.emos.wx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author: GZ
 * @date: 2022/10/11 16:29
 */
@Schema(description = "注册表单信息")
@Data
public class RegisterFormVO {

    @Schema(description = "邀请码")
    @NotBlank(message = "邀请码不能为空")
    @Pattern(regexp = "^[0-9]{6}$",message = "邀请码必须是6位数字")
    private String registerCode;

    @Schema(description = "微信临时授权码")
    @NotBlank(message = "微信临时授权码不能为空")
    private String code;

    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @Schema(description = "头像")
    @NotBlank(message = "头像不能为空")
    private String photo;
}
