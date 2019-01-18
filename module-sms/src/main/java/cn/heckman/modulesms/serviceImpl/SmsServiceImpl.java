package cn.heckman.modulesms.serviceImpl;

import cn.heckman.modulesms.service.SmsService;
import com.alibaba.fastjson.JSON;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {


    public void send(String msg, String phone) {
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient("5f05f19d30111cee80323213414370b9").init();

        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TEXT, msg);
        Result<SmsSingleSend> r = clnt.sms().single_send(param);

        System.out.println(JSON.toJSONString(r.getData()));
        //获取返回结果，返回码:r.getCode(),返回码描述:r.getMsg(),API结果:r.getData(),其他说明:r.getDetail(),调用异常:r.getThrowable()

        //账户:clnt.user().* 签名:clnt.sign().* 模版:clnt.tpl().* 短信:clnt.sms().* 语音:clnt.voice().* 流量:clnt.flow().* 隐私通话:clnt.call().*

        //释放clnt
        clnt.close();
    }


    @Override
    public void sendTempSms(String id, String[] values, String phone) throws Exception {
//        SmsTemp smsTemp = smsTempDao.findOne(id);
//        String content = smsTemp.getTempContenmt();
//        String[] variables = smsTemp.getVariables().split(",");
//        for (int i = 0; i < variables.length; i++) {
//            content = content.replace(variables[i], values[i]);
//        }
//        send(content, phone);
    }
}
