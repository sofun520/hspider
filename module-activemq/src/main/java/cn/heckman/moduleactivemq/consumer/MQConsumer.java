package cn.heckman.moduleactivemq.consumer;

import org.apache.activemq.ActiveMQConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class MQConsumer {

    private Logger logger = LoggerFactory.getLogger(MQConsumer.class);

    private String queueName;

    private MessageListener messageListener;

    private ActiveMQConnection connection;

    public MQConsumer(String queueName, MessageListener messageListener, ActiveMQConnection connection) {
        logger.info("初始化consumer=>{}", queueName);
        this.queueName = queueName;
        this.messageListener = messageListener;
        this.connection = connection;
        this.init();
    }

    public void init() {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(messageListener);
            connection.start();
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
