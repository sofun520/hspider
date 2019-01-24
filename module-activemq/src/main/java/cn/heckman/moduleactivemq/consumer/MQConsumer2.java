package cn.heckman.moduleactivemq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class MQConsumer2 {

    private Logger logger = LoggerFactory.getLogger(MQConsumer2.class);

    private String queueName;

    private MessageListener messageListener;

    private Connection connection;

    public MQConsumer2(String queueName, MessageListener messageListener, Connection connection) {
        logger.info("初始化consumer=>{}", queueName);
        this.queueName = queueName;
        this.messageListener = messageListener;
        this.connection = connection;
        this.init();
    }

    public void init() {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue(queueName + "?consumer.prefetchSize=10");
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(messageListener);
            connection.start();
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
