package com.gz.emos.wx.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author: GZ
 * @date: 2022/11/4 10:00
 */
@Data
@Component
public class SystemConstants {
    public String attendanceStartTime;
    public String attendanceTime;
    public String attendanceEndTime;
    public String closingStartTime;
    public String closingTime;
    public String closingEndTime;
}
