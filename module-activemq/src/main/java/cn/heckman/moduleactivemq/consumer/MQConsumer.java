//package cn.heckman.moduleactivemq.consumer;
//
//import cn.heckman.modulecommon.utils.SpringContextUtils;
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.command.ActiveMQQueue;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jms.listener.SimpleMessageListenerContainer;
//
//import javax.jms.*;
//
//public class MQConsumer {
//
//    private Logger logger = LoggerFactory.getLogger(MQConsumer.class);
//
//    private String queueName;
//
//    private MessageListener messageListener;
//
//    private Connection connection;
//
//    public MQConsumer(String queueName, MessageListener messageListener, Connection connection) {
//        logger.info("初始化consumer=>{}", queueName);
//        this.queueName = queueName;
//        this.messageListener = messageListener;
//        this.connection = connection;
//        this.init();
//    }
//
//    public void init() {
//        try {
//            ActiveMQConnectionFactory activeMQConnectionFactory = SpringContextUtils.getBean(ActiveMQConnectionFactory.class);
//            SimpleMessageListenerContainer smlc = new SimpleMessageListenerContainer();
//            smlc.setConcurrentConsumers(4);
//            smlc.setConnectionFactory(activeMQConnectionFactory);
//            smlc.setDestination(new ActiveMQQueue(queueName));
//            smlc.setMessageListener(messageListener);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
