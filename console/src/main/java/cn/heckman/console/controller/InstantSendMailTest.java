package cn.heckman.console.controller;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 不间断连续发送测试
 *
 * @author hhb@richinfo.cn
 * @ClassName: InstantSendMailTest
 * @Description: TODO
 * @date 2015-12-11 下午02:28:50
 */
public class InstantSendMailTest {
    public static void sendMail2(String _receiverMail) throws UnsupportedEncodingException {
        String appKey = "7c1d6837-dcae-f17d-b979-ae7f0c67998c";
        String appSecret= "671a7f95-6903-28f1-78ca-04db4cbac493";
        String url = "http://211.136.10.228:8580/SendMailModule/SendSmsMailService"; 
        String methodName = "sendmail";
        String uid = "youjianplus@139.com";
        String receiverMail = _receiverMail;
        String content = Base64.encode("邮件内容".getBytes("utf-8"));
        String smsContent = "【彩讯科技】您的验证码是168888，如非本人发送请忽略该信息。";
        String title = Base64.encode((smsContent).getBytes("UTF-8"));
        String email_title = Base64.encode("这是一个email_title".getBytes("UTF-8"));
        Date date = new Date();
        date.setTime(date.getTime() + 40000000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(date);
        String spnumber = "";

        String templateId = "1000295";
        //短信服务
        String serviceType = "SMS";

        String beforeEncript = "app_key" + appKey + "app_secret" + appSecret + "content" + content
                + "email_title"+ email_title
                + "receiver_mail" + receiverMail
//                + "receiver_name" + receiver_name
                + "request_method" + methodName + "return_formatxml"
                + "sendsmspriority2serviceType"+serviceType+"spnumber" + spnumber
                + "templateId" + templateId
                + "timestamp" + timestamp
                //+"sendsmspriority2templateId"+templateId+"timestamp" + timestamp
                + "title" + title
//                + "username" + username
                + "usernumber" + uid + "version2.0";

        String afterEncript = encriptMD5(beforeEncript).toUpperCase();
        System.out.println("待加密字符串：" + beforeEncript);
        System.out.println("加密后的字符串" + afterEncript);
        StringBuffer testXML = new StringBuffer();
        testXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<sendmail>")
                .append("<sendsmspriority>2</sendsmspriority>")
                .append("<templateId>"+templateId+"</templateId>")
                .append("<version>2.0</version>")
                .append("<request_method>sendmail</request_method>")
                .append("<app_key>" + appKey + "</app_key>")
                .append("<receiver_mail>" + receiverMail + "</receiver_mail>")
//                .append("<receiver_name>" + receiver_name + "</receiver_name>")
                .append("<content>" + content + "</content>")
                .append("<title>" + title + "</title>")
                .append("<spnumber>" + spnumber + "</spnumber>")
                .append("<serviceType>" +serviceType+ "</serviceType>")
//                .append("<username>" + username + "</username>")
                .append("<return_format>xml</return_format>")
                .append("<app_secret>" + appSecret + "</app_secret>")
                .append("<usernumber>" + uid + "</usernumber>")
                .append("<sign_code>" + afterEncript + "</sign_code>")
                .append("<timestamp>" + timestamp + "</timestamp>")
                .append("<email_title>"+ email_title+"</email_title>")
                .append("</sendmail>");
        System.out.println("请求XML：" + testXML.toString());
        long startTime = System.currentTimeMillis();
        String resStr = httpRequest(testXML.toString(), url);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - startTime) / 1000 + "返回XML：" + resStr);
    }

    public static String encriptMD5(String text) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String httpRequest(String reqXml, String reqUrl) {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        BufferedReader respin = null;
        StringBuffer respData = null;
        String respStr = null;
        try {
            url = new URL(reqUrl);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setConnectTimeout(10000);// 连接超时时间
            httpurlconnection.setReadTimeout(30000);// 读超时时间
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setRequestProperty("Content-Type",
                    "text/xml;charset=UTF-8");
            httpurlconnection.getOutputStream().write(reqXml.getBytes("UTF-8"));
            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();
            int code = httpurlconnection.getResponseCode();
            respin = new BufferedReader(new InputStreamReader(
                    httpurlconnection.getInputStream(), "utf-8"));
            respData = new StringBuffer(300);
            while ((respStr = respin.readLine()) != null) {
                respData.append(respStr);
            }
            return respData.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            respData = null;
            if (respin != null) {
                try {
                    respin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                respin = null;
            }
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
                httpurlconnection = null;
            }
        }
    }

    public static void main(String[] args) throws Exception {
      
    	sendMail2("18871012630@139.com");
    	
    }

    @SuppressWarnings("unused")
    private static String[] distinctAddress(String[] recipientEmailAddrs) {
        String tempStr = "";
        for (int i = 0; i < recipientEmailAddrs.length; i++) {
            if (!tempStr.contains(recipientEmailAddrs[i])) {
                tempStr += recipientEmailAddrs[i] + ",";
            }
        }
        tempStr = tempStr.substring(0, tempStr.length() - 1);
        return tempStr.split(",");
    }

    public static void jiami(String str){
        System.out.println(encriptMD5(str).toUpperCase());
    }
}
