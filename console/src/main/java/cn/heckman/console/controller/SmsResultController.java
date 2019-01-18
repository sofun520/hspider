package cn.heckman.console.controller;

import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.ErrorConstant;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.MQTopic;
import cn.heckman.moduleservice.service.SmsResultService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sms_result")
@SuppressWarnings("all")
public class SmsResultController extends AbstractPortalController {

    @Autowired
    private SmsResultService smsResultService;

    @Autowired
    private MQProducer producerService;

    private Logger logger = LoggerFactory.getLogger(SmsResultController.class);

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public RestResponse page(@RequestParam(defaultValue = "1") Integer pageNo,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             String phone,
                             String message) {
        RestResponse restResponse = new RestResponse();
        Map<String, Object> param = new HashMap<>();
        try {
            if(StringUtils.isNotBlank(phone)){
                param.put("phone",phone);
            }
            if(StringUtils.isNotBlank(message)){
                param.put("message",message);
            }
            Page page = smsResultService.queryPage(param, pageNo, pageSize);
            restResponse.setSuccess(true);
            restResponse.setData(page);
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return restResponse;
    }

    @RequestMapping("/report")
    public String SmsReport(String sms_status) {
        RestResponse restResponse = new RestResponse();
        try {
            if (StringUtils.isNotBlank(sms_status)) {
                String result = URLDecoder.decode(sms_status);

//                Destination destination = new ActiveMQQueue(MQTopic.SMS_REPORT);
                //将状态报告推送到mq，从backend服务入库
                producerService.sendMessage(MQTopic.SMS_REPORT, result);
            }
        } catch (Exception ex) {
            logger.error("inner error", ex);
            RestResponse.failed(ErrorConstant.INNER_ERROR_CODE, ErrorConstant.getErrMsg(ErrorConstant.INNER_ERROR_CODE));
        }
        return "SUCCESS";
    }

}
