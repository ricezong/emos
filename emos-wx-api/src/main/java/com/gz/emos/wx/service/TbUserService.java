package com.gz.emos.wx.service;

import com.gz.emos.wx.domain.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
* @author zong
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2022-09-03 12:16:47
*/
public interface TbUserService extends IService<TbUser> {

    /**
     * 用户注册
     * @param registerCode 邀请码
     * @param code 微信临时授权码
     * @param nickName 微信昵称
     * @param photo 微信头像
     * @return
     */
    int registerUser(String registerCode,String code,String nickName,String photo);

    /**
     * 获取用户权限
     * @param userId 用户ID
     * @return
     */
    Set<String> searchUserPermissions(int userId);

    /**
     * 用户登录
     * @param code 微信临时授权码
     * @return
     */
    Integer login(String code);

    /**
     * 通过id查询用户信息
     * @param userId
     * @return
     */
    TbUser searchById(int userId);

}
