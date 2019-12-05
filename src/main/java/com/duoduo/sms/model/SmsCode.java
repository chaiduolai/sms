package com.duoduo.sms.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
/**
 * @author duoduo
 * @date 2019-12-04 18:21:22
 * @email cduolai@163.com
 */
@Data
@TableName("sms_code")
public class SmsCode implements Serializable {

    private Long id;

    // 手机号
    private String phone;

    // 验证码
    private String code;

    // 过期时间
    private Date expiredTime;

    // 创建时间
    private Date createTime;

}