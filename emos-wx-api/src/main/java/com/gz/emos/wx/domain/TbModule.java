package com.gz.emos.wx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 模块资源表
 * @TableName tb_module
 */
@TableName(value ="tb_module")
@Data
public class TbModule implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 模块编号
     */
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}