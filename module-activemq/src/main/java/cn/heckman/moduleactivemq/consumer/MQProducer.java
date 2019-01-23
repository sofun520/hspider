package cn.heckman.moduleactivemq.consumer;

import cn.heckman.modulecommon.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

@Component
public class MQProducer {

    private Logger logger = LoggerFactory.getLogger(MQProducer.class);

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    public void sendMessage(String queueName, final String message) {
        try {
//            logger.info("queueName=>{}, message=>{}", queueName, message);
            jmsTemplate.convertAndSend(queueName, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
