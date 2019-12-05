package com.duoduo.sms.config;

import com.duoduo.sms.util.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: SmsConfig
 * @program: sms->SmsConfig
 * @Description: 初始化短信工具
 * @author: duolai
 * @create: 2019-12-04 19:15
 **/
@Configuration
public class SmsConfig {
    @Autowired
    private SystemConfig systemConfig;

    @Bean
    public SMSUtil sms(){
        return new SMSUtil(systemConfig.getSms().getServerIp(),systemConfig.getSms().getServerPort(),systemConfig.getSms().getAccountSid(),
                systemConfig.getSms().getAuthToken(),systemConfig.getSms().getAppId(),systemConfig.getSms().getSecurityCodeTemplate());
    }
}
