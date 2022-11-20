package com.gz.emos.wx.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gz.emos.wx.config.SystemConstants;
import com.gz.emos.wx.domain.TbCheckin;
import com.gz.emos.wx.mapper.TbHolidaysMapper;
import com.gz.emos.wx.mapper.TbWorkdayMapper;
import com.gz.emos.wx.service.TbCheckinService;
import com.gz.emos.wx.mapper.TbCheckinMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author zong
 * @description 针对表【tb_checkin(签到表)】的数据库操作Service实现
 * @createDate 2022-09-03 12:16:47
 */
@Service
@Scope("prototype")//多例线程可以异步执行
@Slf4j
public class TbCheckinServiceImpl extends ServiceImpl<TbCheckinMapper, TbCheckin>
        implements TbCheckinService {

    private static final String HOLIDAY = "节假日";

    private static final String WORKDAY = "工作日";

    @Autowired
    private SystemConstants systemConstants;

    @Autowired
    private TbHolidaysMapper holidaysMapper;

    @Autowired
    private TbWorkdayMapper workdayMapper;

    @Autowired
    private TbCheckinMapper checkinMapper;


    @Override
    public String validCanCheckIn(int userId, String date) {
        //当天是否是节假日
        boolean bool_1 = holidaysMapper.searchTodayIsHolidays() != null;
        //当天是否是工作日
        boolean bool_2 = workdayMapper.searchTodayIsWorkday() != null;
        String type = WORKDAY;
        if (DateUtil.date().isWeekend()) {
            type = HOLIDAY;
        }
        if (bool_1) {
            type = HOLIDAY;
        } else if (bool_2) {
            type = WORKDAY;
        }
        if (type.equals(HOLIDAY)) {
            return "节假日无需签到";
        } else {
            DateTime now = DateUtil.date();
            String start = DateUtil.today() + " " + systemConstants.attendanceStartTime;
            String end = DateUtil.today() + " " + systemConstants.attendanceEndTime;
            DateTime attendanceStart = DateUtil.parse(start);
            DateTime attendanceEnd = DateUtil.parse(end);
            if (now.isBefore(attendanceStart)) {
                return "未到签到时间";
            } else if (now.isAfter(attendanceEnd)) {
                return "签到已截止";
            } else {
                HashMap<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(userId));
                map.put("date", date);
                map.put("start", start);
                map.put("end", end);
                boolean b = checkinMapper.haveCheckin(map) != null;
                if (b) {
                    return "今日已签到";
                } else {
                    return "签到成功";
                }
            }
        }
    }
}




