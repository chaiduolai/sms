package com.duoduo.sms.service;

public interface SmsService {
    /**
     * 发送登录验证短信
     *
     * @param phone
     */
    void sendLoginSms(String phone);
}
