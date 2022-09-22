package com.gz.emos.wx.config.shiro;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类，用来 加密Token 和 验证Token 的有效性
 */
@Component
@Slf4j
public class JwtUtil {

    //密钥
    @Value("${emos.jwt.secret}")
    private String secret;
    //过期时间
    @Value("${emos.jwt.expire}")
    private int expire;

    /**
     * 创建token
     *
     * @param userId
     * @return
     */
    public String createToken(int userId) {
        //过期时间
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 5);
        //加密算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //创建令牌
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        return token;
    }

    /**
     * 从token获取userId
     * @param token
     * @return
     */
    public int getUserId(String token){
        //解码
        DecodedJWT jwt=JWT.decode(token);
        Integer userId = jwt.getClaim("userId").asInt();
        return userId;
    }

    /**
     * 验证token时效性
     * @param token
     */
    public void verifierToken(String token){
        //创建加密算法对象
        Algorithm algorithm=Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier=JWT.require(algorithm).build();
        jwtVerifier.verify(token);
    }
}
