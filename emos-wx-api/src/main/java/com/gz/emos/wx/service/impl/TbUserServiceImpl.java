package com.gz.emos.wx.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gz.emos.wx.domain.TbUser;
import com.gz.emos.wx.exception.EmosException;
import com.gz.emos.wx.service.TbUserService;
import com.gz.emos.wx.mapper.TbUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author zong
 * @description 针对表【tb_user(用户表)】的数据库操作Service实现
 * @createDate 2022-09-03 12:16:47
 */
@Service
@Slf4j
@Scope("prototype")
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public int registerUser(String registerCode, String code, String nickName, String photo) {
        //如果邀请码是000000，代表是超级管理员
        if (registerCode.equals("000000")) {
            //查询超级管理员帐户是否已经绑定
            boolean bool = tbUserMapper.haveRootUser();
            if (!bool) {
                //把当前用户绑定到ROOT帐户
                String openId = getOpenId(code);
                HashMap<String,Object> param = new HashMap<>();
                param.put("openId", openId);
                param.put("nickname", nickName);
                param.put("photo", photo);
                param.put("role", "[0]");
                param.put("status", 1);
                param.put("createTime", new Date());
                param.put("root", true);
                tbUserMapper.insert(param);
                int id = tbUserMapper.searchIdByOpenId(openId);
                return id;
            } else {
                //如果root已经绑定了，就抛出异常
                throw new EmosException("无法绑定超级管理员账号");
            }
        }
        //TODO 此处还有其他判断内容
        else {
            return 0;
        }

    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions = tbUserMapper.searchUserPermissions(userId);
        return permissions;
    }

    @Override
    public Integer login(String code) {
        String openId=getOpenId(code);
        Integer id = tbUserMapper.searchIdByOpenId(openId);
        if (id==null){
            throw new EmosException("账户不存在！");
        }
        //TODO 从消息队列中接收消息，转移到消息表
        return id;
    }

    @Override
    public TbUser searchById(int userId) {
        TbUser tbUser = tbUserMapper.searchById(userId);
        return tbUser;
    }

    /**
     * 获取微信授权码
     * @param code 微信临时授权码
     * @return 微信授权码
     */
    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");
        if (openId == null || openId.length() == 0) {
            throw new RuntimeException("临时登陆凭证错误");
        }
        return openId;
    }
}




