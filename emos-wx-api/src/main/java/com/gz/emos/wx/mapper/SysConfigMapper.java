package com.gz.emos.wx.mapper;

import com.gz.emos.wx.domain.SysConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author zong
* @description 针对表【sys_config】的数据库操作Mapper
* @createDate 2022-09-03 12:16:47
* @Entity com.gz.emos.wx.domain.SysConfig
*/
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    List<SysConfig> selectAllParam();

}




