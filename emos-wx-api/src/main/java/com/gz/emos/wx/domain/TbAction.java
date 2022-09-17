package com.gz.emos.wx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 行为表
 * @TableName tb_action
 */
@TableName(value ="tb_action")
@Data
public class TbAction implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 行为编号
     */
    private String actionCode;

    /**
     * 行为名称
     */
    private String actionName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}