package cn.heckman.backend.mqlistener;

import cn.heckman.backend.storedMap.BdbPersistentQueue;
import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.modulecommon.utils.ConstUtils;
import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.moduleservice.entity.SmsGatewayTest;
import cn.heckman.moduleservice.service.SmsGatewayTestService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SmsMTListener implements MessageListener {

    public static BdbPersistentQueue<String> queue = new BdbPersistentQueue<String>(ConstUtils.getStr("bdb.path"), "sms_mt", String.class);

    @Override
    public void onMessage(Message message) {
        try {
//            System.out.println(queue.size());
            SmsGatewayTestService smsGatewayTestService = SpringContextUtils.getBean(SmsGatewayTestService.class);
            MQProducer producerService = SpringContextUtils.getBean(MQProducer.class);
//            JSONObject result = JSON.parseObject(text.getText());

            if (message instanceof TextMessage) {
                TextMessage text = (TextMessage) message;

                if (StringUtils.isNotBlank(text.getText())) {
                    SmsGatewayTest smsGatewayTest = JSON.parseObject(text.getText(), SmsGatewayTest.class);
//                String bdbPath = ConstUtils.getStr("bdb.path");
                    queue.offer(text.getText());
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
