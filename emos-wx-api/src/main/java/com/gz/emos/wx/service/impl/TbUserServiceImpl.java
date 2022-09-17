package com.gz.emos.wx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gz.emos.wx.domain.TbUser;
import com.gz.emos.wx.service.TbUserService;
import com.gz.emos.wx.mapper.TbUserMapper;
import org.springframework.stereotype.Service;

/**
* @author zong
* @description 针对表【tb_user(用户表)】的数据库操作Service实现
* @createDate 2022-09-03 12:16:47
*/
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService{

}




