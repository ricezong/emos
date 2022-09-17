package com.gz.emos.wx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 节假日表
 * @TableName tb_holidays
 */
@TableName(value ="tb_holidays")
@Data
public class TbHolidays implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 日期
     */
    private Date date;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}