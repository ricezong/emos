package com.gz.emos.wx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gz.emos.wx.domain.TbCity;
import com.gz.emos.wx.service.TbCityService;
import com.gz.emos.wx.mapper.TbCityMapper;
import org.springframework.stereotype.Service;

/**
* @author zong
* @description 针对表【tb_city(疫情城市列表)】的数据库操作Service实现
* @createDate 2022-09-03 12:16:47
*/
@Service
public class TbCityServiceImpl extends ServiceImpl<TbCityMapper, TbCity>
    implements TbCityService{

}




