package com.gz.emos.wx.mapper;

import com.gz.emos.wx.domain.TbHolidays;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author zong
* @description 针对表【tb_holidays(节假日表)】的数据库操作Mapper
* @createDate 2022-09-03 12:16:47
* @Entity com.gz.emos.wx.domain.TbHolidays
*/
public interface TbHolidaysMapper extends BaseMapper<TbHolidays> {

    Integer searchTodayIsHolidays();

}




