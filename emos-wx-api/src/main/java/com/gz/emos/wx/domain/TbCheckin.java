package com.gz.emos.wx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 签到表
 * @TableName tb_checkin
 */
@TableName(value ="tb_checkin")
@Data
public class TbCheckin implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 用户ID
     */
    private Object userId;

    /**
     * 签到地址
     */
    private String address;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区划
     */
    private String district;

    /**
     * 考勤结果
     */
    private Integer status;

    /**
     * 风险等级
     */
    private Object risk;

    /**
     * 签到日期
     */
    private Date date;

    /**
     * 签到时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}