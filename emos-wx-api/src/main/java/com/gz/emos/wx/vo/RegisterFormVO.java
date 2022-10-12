package com.gz.emos.wx.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author: GZ
 * @date: 2022/10/11 16:29
 */
@ApiModel(description = "注册表单信息")
@Data
public class RegisterFormVO {

    @ApiModelProperty("邀请码")
    @NotBlank(message = "邀请码不能为空")
    @Pattern(regexp = "^[0-9]{6}$",message = "邀请码必须是6位数字")
    private String registerCode;

    @ApiModelProperty("微信临时授权码")
    @NotBlank(message = "微信临时授权码不能为空")
    private String code;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @ApiModelProperty("头像")
    @NotBlank(message = "头像不能为空")
    private String photo;
}
