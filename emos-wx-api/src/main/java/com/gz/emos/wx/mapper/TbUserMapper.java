package com.gz.emos.wx.mapper;

import com.gz.emos.wx.domain.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.HashMap;
import java.util.Set;

/**
 * @author zong
 * @description 针对表【tb_user(用户表)】的数据库操作Mapper
 * @createDate 2022-09-03 12:16:47
 * @Entity com.gz.emos.wx.domain.TbUser
 */
public interface TbUserMapper extends BaseMapper<TbUser> {

    boolean haveRootUser();

    int insert(HashMap<String,Object> param);

    //sql如果没有查询到结果会返回空值，这里返回类型用Integer是因为int不能保存空值
    Integer searchIdByOpenId(String openId);

    Set<String> searchUserPermissions(int userId);

    TbUser searchById(int userId);
}




