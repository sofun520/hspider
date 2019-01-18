package cn.heckman.backend.controller;

import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.modulecommon.utils.UUIDGenerator;
import cn.heckman.moduleredis.service.RedisService;
import cn.heckman.moduleservice.base.AbstractPortalController;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.MQTopic;
import cn.heckman.moduleservice.entity.SmsGatewayAccount;
import cn.heckman.moduleservice.service.SmsGatewayAccountService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

@RequestMapping("/sms")
@RestController
public class SmsGatewayApi extends AbstractPortalController {

    @Autowired
    private MQProducer producerService;

    @Autowired
    private SmsGatewayAccountService smsGatewayAccountService;

    @Autowired
    private RedisService redisService;

    private Logger logger = LoggerFactory.getLogger(SmsGatewayApi.class);

    @RequestMapping("/v1/send")
    public RestResponse v1(String account,
                           String userId,
                           String password,
                           String phone) {
        RestResponse restResponse = new RestResponse();
        try {
            logger.info("{}提交，号码：{}", account, phone);
            if (StringUtils.isBlank(account)) {
                return RestResponse.failed("1001", "账号不能为空");
            }
            if (StringUtils.isBlank(userId)) {
                return RestResponse.failed("1002", "账号id不能为空");
            }
            if (StringUtils.isBlank(password)) {
                return RestResponse.failed("1003", "密码不能为空");
            }
            if (StringUtils.isBlank(phone)) {
                return RestResponse.failed("1004", "phone不能为空");
            }

//            List<SmsGatewayAccount> list = smsGatewayAccountService.checkaccount(account, userId, password);
//            if (list.size() == 0) {
//                return RestResponse.failed("1005", "account或userId或password不正确");
//            }

            //从redis里读取api用户信息
            String a = (String) redisService.get("gateway_user_" + userId);
            SmsGatewayAccount smsGatewayAccount = JSON.parseObject(a, SmsGatewayAccount.class);
            if (!account.equals(smsGatewayAccount.getAccount()) || !password.equals(smsGatewayAccount.getPassword())) {
                return RestResponse.failed("1005", "account或userId或password不正确");
            }


//            if (!"tester".equals(account) || !"60001".equals(userId) || !"123abc".equals(password)) {
//                return RestResponse.failed("1005", "account或userId或password不正确");
//            }

            String taskId = UUIDGenerator.uuid();
            JSONObject re = new JSONObject();
            re.put("taskId", taskId);
            re.put("phone", phone);
            re.put("account", account);

//            Destination destination = new ActiveMQQueue(MQTopic.SMS_GATEWAY_TEST);
//            Destination destination = new ActiveMQQueue(MQTopic.SMS_REQ);
            //推送到mq，从backend服务入库
            producerService.sendMessage(MQTopic.SMS_REQ, re.toJSONString());

//            Map<String, String> data = new HashMap<>();
//            data.put("taskId", taskId);
//            restResponse.setData(data);
            restResponse.setData(taskId);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return restResponse;
    }

}
