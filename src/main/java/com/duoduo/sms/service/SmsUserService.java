package com.duoduo.sms.service;

import com.duoduo.sms.model.SmsUser;

public interface SmsUserService {
    /**
     * 查询账号是否存存在
     */
    SmsUser selectByPhone(String phone);
}
