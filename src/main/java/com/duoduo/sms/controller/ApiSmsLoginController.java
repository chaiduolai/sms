package com.duoduo.sms.controller;

import com.duoduo.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ApiSmsLoginController
 * @program: sms->ApiSmsLoginController
 * @Description: 短信验证码登录接口
 * @author: duolai
 * @create: 2019-12-04 18:27
 **/
@RestController
@RequestMapping("/api/login")
public class ApiSmsLoginController {
    @Autowired
    private SmsService smsService;

    @PostMapping("/sendLoginSms")
    public void sendLoginSms(@RequestParam String phone){
        smsService.sendLoginSms(phone);
    }
}
