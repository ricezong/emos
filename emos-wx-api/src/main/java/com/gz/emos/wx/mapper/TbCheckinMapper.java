package com.gz.emos.wx.mapper;

import com.gz.emos.wx.domain.TbCheckin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.HashMap;

/**
* @author zong
* @description 针对表【tb_checkin(签到表)】的数据库操作Mapper
* @createDate 2022-09-03 12:16:47
* @Entity com.gz.emos.wx.domain.TbCheckin
*/
public interface TbCheckinMapper extends BaseMapper<TbCheckin> {

    Integer haveCheckin(HashMap<String,String> map);

}




