package com.duoduo.sms.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.duoduo.sms.util.SMSUtil;
import com.duoduo.sms.dao.SmsCodeDao;
import com.duoduo.sms.model.SmsCode;
import com.duoduo.sms.model.SmsUser;
import com.duoduo.sms.service.SmsService;
import com.duoduo.sms.service.SmsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @ClassName: SmsServiceImpl
 * @program: sms->SmsServiceImpl
 * @Description: 发送短信验证码实现类
 * @author: duolai
 * @create: 2019-12-04 18:30
 **/
@Service
public class SmsServiceImpl implements SmsService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SmsUserService smsUserService;
    @Autowired
    private SmsCodeDao smsCodeDao;
    @Autowired
    private SMSUtil sms;
    /**
     * 发送登录验证短信
     *
     * @param phone
     */
    @Override
    public void sendLoginSms(String phone) {
//        首先查询该账号是否存在
        SmsUser smsUser = smsUserService.selectByPhone(phone);
        if (StringUtils.isEmpty(smsUser)){
            throw new RuntimeException("手机号码不存在");
        }
//        随机生成一个六位数的短信验证码
        String code= RandomUtil.randomNumbers(6);
//        通过手机号码查询短信配置表里面是否有数据，若果有就覆盖
        querySmsCode(phone,code);
        logger.info("手机号{}的验证码为:{}",phone,code);
        try {
            sms.sendMessage(phone,sms.getSecurityCodeTemplate(),new String[]{code});
        } catch (Exception e) {
            logger.error("发送验证码失败",e);
            throw new RuntimeException("发送验证码失败");

        }
    }
/**
 * @Description: 查询并更新数据库验证码信息
 * @Param: [phone, code]
 * @Author: duolai
 * @Date: 2019/12/4
 * @return: void
*/
    private void querySmsCode(String phone,String code) {
        SmsCode smsCode=new SmsCode();
        smsCode.setPhone(phone);
        smsCode = smsCodeDao.selectOne(smsCode);
        if (StringUtils.isEmpty(smsCode)){
            smsCode=new SmsCode();
            smsCode.setPhone(phone);
            smsCode.setCode(code);
            smsCode.setCreateTime(new Date());
            smsCode.setExpiredTime(SMSUtil.getExpiredTime());
            smsCodeDao.insert(smsCode);
        }else {
            smsCode.setCode(code);
            smsCode.setExpiredTime(SMSUtil.getExpiredTime());
            smsCode.setCreateTime(new Date());
            smsCodeDao.updateById(smsCode);
        }
    }
}
