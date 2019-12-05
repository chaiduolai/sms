package com.duoduo.sms.config;

import cn.hutool.json.JSONUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName: SystemConfig
 * @program: sms->SystemConfig
 * @Description: 系统配置
 * @author: duolai
 * @create: 2019-12-04 19:16
 **/
@Configuration
@ConfigurationProperties(prefix = "system")
@PropertySource("classpath:config/systemConfig-${spring.profiles.active}.properties")//注意路径
public class SystemConfig {

    public SystemConfig(){
        this.instance=this;
    }

    private static SystemConfig instance;

    public static SystemConfig getInstance(){
        return instance;
    }

    private SmsConfig sms;



    public SmsConfig getSms() {
        return sms;
    }

    public void setSms(SmsConfig sms) {
        this.sms = sms;
    }

    public static class SmsConfig{
        private String serverIp;
        private String serverPort;
        private String accountSid;
        private String authToken;
        private String appId;
        private String securityCodeTemplate;

        public String getServerIp() {
            return serverIp;
        }

        public void setServerIp(String serverIp) {
            this.serverIp = serverIp;
        }

        public String getServerPort() {
            return serverPort;
        }

        public void setServerPort(String serverPort) {
            this.serverPort = serverPort;
        }

        public String getAccountSid() {
            return accountSid;
        }

        public void setAccountSid(String accountSid) {
            this.accountSid = accountSid;
        }

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getSecurityCodeTemplate() {
            return securityCodeTemplate;
        }

        public void setSecurityCodeTemplate(String securityCodeTemplate) {
            this.securityCodeTemplate = securityCodeTemplate;
        }

        @Override
        public String toString() {
            return JSONUtil.toJsonStr(this);
        }
    }
}

