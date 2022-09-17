package com.gz.emos.wx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_permission
 */
@TableName(value ="tb_permission")
@Data
public class TbPermission implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 权限
     */
    private String permissionName;

    /**
     * 模块ID
     */
    private Object moduleId;

    /**
     * 行为ID
     */
    private Object actionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}