package com.gz.emos.wx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 疫情城市列表
 * @TableName tb_city
 */
@TableName(value ="tb_city")
@Data
public class TbCity implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 拼音简称
     */
    private String code;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}