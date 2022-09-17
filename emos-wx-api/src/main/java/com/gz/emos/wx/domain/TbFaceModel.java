package com.gz.emos.wx.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_face_model
 */
@TableName(value ="tb_face_model")
@Data
public class TbFaceModel implements Serializable {
    /**
     * 主键值
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 用户ID
     */
    private Object userId;

    /**
     * 用户人脸模型
     */
    private String faceModel;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}