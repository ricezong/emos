package com.gz.emos.wx.mapper;

import com.gz.emos.wx.domain.TbWorkday;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author zong
* @description 针对表【tb_workday】的数据库操作Mapper
* @createDate 2022-09-03 12:16:47
* @Entity com.gz.emos.wx.domain.TbWorkday
*/
public interface TbWorkdayMapper extends BaseMapper<TbWorkday> {

    Integer searchTodayIsWorkday();

}




