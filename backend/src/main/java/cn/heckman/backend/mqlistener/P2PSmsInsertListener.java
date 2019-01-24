package cn.heckman.backend.mqlistener;

import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.moduleservice.entity.MQTopic;
import cn.heckman.moduleservice.entity.SmsGatewayTest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jms.JmsException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 点对点短信入库监听
 */
@SuppressWarnings("all")
public class P2PSmsInsertListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                MQProducer producerService = SpringContextUtils.getBean(MQProducer.class);
                TextMessage text = (TextMessage) message;
                JSONObject a = JSONObject.parseObject(text.getText());

                SmsGatewayTest smsGatewayTest = null;
                JSONArray array = a.getJSONArray("list");
                JSONObject obj = null;
                for (int i = 0; i < array.size(); i++) {
                    obj = array.getJSONObject(i);
                    smsGatewayTest = new SmsGatewayTest(a.getString("taskId"), obj.getString("phone"), SmsGatewayTest.PADING_SEND, a.getString("account"));
                    producerService.sendMessage(MQTopic.SMS_MT, JSON.toJSONString(smsGatewayTest));
                }
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
