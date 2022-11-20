package com.gz.emos.wx;

import cn.hutool.core.util.StrUtil;
import com.gz.emos.wx.config.SystemConstants;
import com.gz.emos.wx.domain.SysConfig;
import com.gz.emos.wx.mapper.SysConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

/**
 * SpringBootApplication 上使用@ServletComponentScan 注解后
 * Servlet可以直接通过@WebServlet注解自动注册
 * Filter可以直接通过@WebFilter注解自动注册
 * Listener可以直接通过@WebListener 注解自动注册
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.gz.emos.wx.mapper")
@Slf4j
public class EmosWxApiApplication {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Autowired
    private SystemConstants constants;

    public static void main(String[] args) {
        SpringApplication.run(EmosWxApiApplication.class, args);
    }

    //@PostConstruct注解的方法在项目启动的时候执行这个方法，
    //也可以理解为在spring容器启动的时候执行，
    //可作为一些数据的常规化加载，比如数据字典之类的。
    @PostConstruct
    public void init(){
        List<SysConfig> configs = sysConfigMapper.selectAllParam(); 
        configs.forEach(config->{
            String paramKey = config.getParamKey();
            paramKey= StrUtil.toCamelCase(paramKey);
            String paramValue = config.getParamValue();
            try{
                //因为不确定数据库传过来的字段具体是哪个，所以这里通过反射赋值
                Field field = constants.getClass().getDeclaredField(paramKey);
                field.set(constants,paramValue);
            }catch (Exception e){
                log.error("执行异常",e);
            }
        });
    }
}
