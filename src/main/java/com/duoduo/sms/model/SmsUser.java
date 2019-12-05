package com.duoduo.sms.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @author duoduo
 * @date 2019-12-04 18:21:22
 * @email cduolai@163.com
 */
@Data
@TableName("sms_user")
public class SmsUser implements Serializable {

    // 主键
    private Long id;

    // 手机号码
    private String phone;

    // 最近一次登录时间
    private Date loginTime;

}