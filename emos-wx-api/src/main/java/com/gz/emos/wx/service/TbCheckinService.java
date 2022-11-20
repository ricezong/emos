package com.gz.emos.wx.service;

import com.gz.emos.wx.domain.TbCheckin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zong
* @description 针对表【tb_checkin(签到表)】的数据库操作Service
* @createDate 2022-09-03 12:16:47
*/
public interface TbCheckinService extends IService<TbCheckin> {

    /**
     * 判断当日是否可以签到
     * @param userId
     * @param date
     * @return
     */
    String validCanCheckIn(int userId,String date);

}
