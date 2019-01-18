package cn.heckman.console.controller;

import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.modulecommon.utils.ConstUtils;
import cn.heckman.modulemail.service.Mailservice;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.MQTopic;
import cn.heckman.moduleservice.entity.MailTemp;
import cn.heckman.moduleservice.service.MailTempService;
import cn.heckman.moduleservice.service.SmsResultService;
import cn.heckman.modulesms.service.SmsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;

@RestController
@RequestMapping("/msg")
public class TestMailContorller {

    private Logger logger = LoggerFactory.getLogger(TestMailContorller.class);

    @Autowired
    private Mailservice mailservice;

    @Autowired
    private MailTempService mailTempService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsResultService smsResultService;

    @RequestMapping("/mail/send")
    public RestResponse send() {
        RestResponse restResponse = new RestResponse();
        try {
            System.out.println(ConstUtils.getStr("spring.mail.username"));

            MailTemp mailTemp = mailTempService.findById("472bd60bf6a211e8ab9c0242ac110003");

            mailservice.sendHtmlMail("测试邮件", mailTemp.getTemp(), "liqiang@konyun.net", "", ConstUtils.getStr("spring.mail.username"));


        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping("/sms/send")
    public RestResponse smsSend() {
        RestResponse restResponse = new RestResponse();
        try {
            smsService.sendTempSms("1433387", new String[]{"123456"}, "18694054311");
//            smsService.send("【京汉安驰】您的验证码是1988989", "18694054311");
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @Autowired
    private MQProducer producerService;

    @RequestMapping("/sms/report")
    public String SmsReport(String sms_status) {
        RestResponse restResponse = new RestResponse();
        try {
            if (StringUtils.isNotBlank(sms_status)) {
                String result = URLDecoder.decode(sms_status);

//                Destination destination = new ActiveMQQueue(MQTopic.SMS_REPORT);
                producerService.sendMessage(MQTopic.SMS_REPORT, result);

                /*JSONArray array = JSON.parseArray(result);
                JSONObject object = null;
                for (int i = 0; i < array.size(); i++) {
                    object = array.getJSONObject(i);
                    smsResultService.updateByPhoneSid(object.getString("report_status") + "",
                            object.getString("error_msg") + "",
                            object.getString("mobile"),
                            object.getString("sid"));
                }*/
            }

        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return "SUCCESS";
    }

}
