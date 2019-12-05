package com.duoduo.sms.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.duoduo.sms.model.SmsCode;
import com.duoduo.sms.model.SmsUser;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsCodeDao extends BaseMapper<SmsCode> {
}
