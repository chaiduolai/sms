package com.duoduo.sms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: Tools
 * @program: sms->Tools
 * @Description: 通用工具类
 * @author: duolai
 * @create: 2019-12-04 19:03
 **/
public class Tools {


    /**
     * @Description:  按照yyyyMMddHHmmss的格式，日期转字符串
     * @Param: [date]
     * @Author: duolai
     * @Date: 2019/12/4
     * @return: java.lang.String
    */
    public static String date2Str(Date date){
        return date2Str(date,"yyyyMMddHHmmss");
    }
    /**
     * @Description:  按照参数format的格式，日期转字符串
     * @Param: [date, format]
     * @Author: duolai
     * @Date: 2019/12/4
     * @return: java.lang.String
    */
    public static String date2Str(Date date, String format){
        if(date!=null){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }else{
            return "";
        }
    }
    public static String MD5(String str) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();

            int i;

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
