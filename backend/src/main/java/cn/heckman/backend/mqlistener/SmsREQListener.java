package cn.heckman.backend.mqlistener;

import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.moduleservice.entity.MQTopic;
import cn.heckman.moduleservice.entity.SmsGatewayTest;
import cn.heckman.moduleservice.service.SmsGatewayTestService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SmsREQListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            SmsGatewayTestService smsGatewayTestService = SpringContextUtils.getBean(SmsGatewayTestService.class);
            MQProducer producerService = SpringContextUtils.getBean(MQProducer.class);
            if (message instanceof TextMessage) {
                TextMessage text = (TextMessage) message;
                JSONObject result = JSON.parseObject(text.getText());

                String[] phones = result.getString("phone").split(",");
                SmsGatewayTest smsGatewayTest = null;
//                Destination destination = new ActiveMQQueue(MQTopic.SMS_MT);
                for (String phone : phones) {
                    smsGatewayTest = new SmsGatewayTest(result.getString("taskId"), phone, SmsGatewayTest.PADING_SEND, result.getString("account"));
//                smsGatewayTestService.save(smsGatewayTest);
                    producerService.sendMessage(MQTopic.SMS_MT, JSON.toJSONString(smsGatewayTest));
                }
                text.acknowledge();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
//            try {
//                session.recover();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
}
