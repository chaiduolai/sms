package com.duoduo.sms.service.impl;

import com.duoduo.sms.dao.SmsUserDao;
import com.duoduo.sms.model.SmsUser;
import com.duoduo.sms.service.SmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SmsUserServiceImpl
 * @program: sms->SmsUserServiceImpl
 * @Description: 账号实现类
 * @author: duolai
 * @create: 2019-12-04 18:33
 **/
@Service
public class SmsUserServiceImpl implements SmsUserService {
    @Autowired
    private SmsUserDao smsUserDao;
    /**
     * 查询账号是否存存在
     *
     * @param phone
     */
    @Override
    public SmsUser selectByPhone(String phone) {
        SmsUser smsUser=new SmsUser();
        smsUser.setPhone(phone);
        return smsUserDao.selectOne(smsUser);
    }
}
