package com.duoduo.sms.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.ssl.TrustAnyHostnameVerifier;
import cn.hutool.json.JSONObject;
import com.duoduo.sms.http.MyX509TrustManager;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

/**
 * @ClassName: SMS
 * @program: sms->SMS
 * @Description: 短信配置平台
 * @author: duolai
 * @create: 2019-12-04 19:00
 **/
public class SMSUtil {

    private String appId;
    private String accountSid;
    private String authToken;
    private String baseUrl;
    private String securityCodeTemplate;
    public static final int EXPIRED_TIME=30;//有效时长（分）

    public SMSUtil(String serverIp, String serverPort, String accountSid, String authToken, String appid, String securityCodeTemplate){
        this.baseUrl="https://"+serverIp+":"+serverPort+"/2013-12-26/";
        this.accountSid=accountSid;
        this.authToken=authToken;
        this.appId=appid;
        this.securityCodeTemplate=securityCodeTemplate;
    }

    public String getSecurityCodeTemplate(){
        return securityCodeTemplate;
    }

    /**
     * 发送短信
     *
     * @param phones 手机号，多个手机号用逗号分隔
     * @param templateId 模板id
     * @param datas 模板填充数据
     */
    public  void  sendMessage(String phones,String templateId,String [] datas) throws Exception {
        final String url=baseUrl+"Accounts/"+accountSid+"/SMS/TemplateSMS?sig="+getSigParameter();

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("to",phones);
        jsonObject.put("appId",appId);
        jsonObject.put("templateId",templateId);
        jsonObject.put("datas",datas);

        final String json=jsonObject.toString();

        String resultStr=sendHttpsPost(url, json);

        JSONObject result=new JSONObject(resultStr);

        if(!"000000".equals(result.getStr("statusCode"))){
            throw new Exception("错误码=" + result.getStr("statusCode") +" 错误信息= "+result.getStr("statusMsg"));
        }
    }

    /**
     * 使用MD5加密（账户Id + 账户授权令牌 + 时间戳）
     *
     * @return
     */
    private  String getSigParameter(){
        String sig=accountSid+authToken+Tools.date2Str(new Date());//未加密前
        return Tools.MD5(sig).toUpperCase();
    }

    /**
     * 使用Base64编码（账户Id + 冒号 + 时间戳）
     *
     * @return
     */
    private  String getAuthorization(){
        String authorization=accountSid+":"+Tools.date2Str(new Date());//未Base64编码前
        return Base64.encode(authorization);
    }

    private  String sendHttpsPost(String url,String json){
        try{
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL myUrl=new URL(url);
            HttpsURLConnection connection=(HttpsURLConnection)myUrl.openConnection();
            connection.setSSLSocketFactory(ssf);
            connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json;charset=utf-8;");
            connection.setRequestProperty("Accept", "application/json;");
            connection.setRequestProperty("Authorization", getAuthorization());
            connection.setRequestProperty("Content-Length", String.valueOf(json.getBytes().length));
            connection.setDoOutput(true);
            connection.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒
            connection.connect();
            OutputStream os= connection.getOutputStream();
            os.write(json.getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            StringBuffer sb=new StringBuffer("");
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Date getExpiredTime(){
//		设置时间偏移，也就是过期时间
        return DateUtil.offsetMinute(new Date(),EXPIRED_TIME);
    }
}
