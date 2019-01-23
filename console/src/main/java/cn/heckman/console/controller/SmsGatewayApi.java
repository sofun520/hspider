package cn.heckman.console.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

@SuppressWarnings("all")
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

            //从redis里读取api用户信息
            String a = (String) redisService.get("gateway_user_" + userId);
            SmsGatewayAccount smsGatewayAccount = JSON.parseObject(a, SmsGatewayAccount.class);
            if (!account.equals(smsGatewayAccount.getAccount()) || !password.equals(smsGatewayAccount.getPassword())) {
                return RestResponse.failed("1005", "account或userId或password不正确");
            }

            String taskId = UUIDGenerator.uuid();
            JSONObject re = new JSONObject();
            re.put("taskId", taskId);
            re.put("phone", phone);
            re.put("account", account);

            //推送到mq，从backend服务入库
            producerService.sendMessage(MQTopic.SMS_REQ, re.toJSONString());

            restResponse.setData(taskId);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return restResponse;
    }

    /**
     * {
     * "account":"",
     * "userId":"",
     * "password":"",
     * list:[
     * {
     * "phone":"",
     * "msg":""
     * },
     * {
     * "phone":"",
     * "msg":""
     * }
     * ]
     * }
     *
     * @param param
     * @return
     */
    @RequestMapping("/v1/p2p/send")
    public RestResponse sendP2P(@RequestBody JSONObject param) {
        RestResponse restResponse = new RestResponse();
        try {
            logger.info("点对点提交，请求：{}", param.toJSONString());
            if (StringUtils.isBlank(param.getString("account"))) {
                return RestResponse.failed("1001", "账号不能为空");
            }
            if (StringUtils.isBlank(param.getString("userId"))) {
                return RestResponse.failed("1002", "用户id不能为空");
            }
            if (StringUtils.isBlank(param.getString("password"))) {
                return RestResponse.failed("1003", "密码不能为空");
            }
            if (param.getJSONArray("list").size() == 0) {
                return RestResponse.failed("1004", "发送列表不能为空");
            }

            String taskId = UUIDGenerator.uuid();
            JSONObject re = new JSONObject();
            re.put("taskId", taskId);
            re.put("account", param.getString("account"));
            re.put("list", param.getJSONArray("list").toJSONString());

            //推送到mq，从backend服务入库
            producerService.sendMessage(MQTopic.SMS_P2P_REQ, re.toJSONString());

            restResponse.setData(taskId);
            restResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return restResponse;
    }

}
