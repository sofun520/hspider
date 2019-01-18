package cn.heckman.backend;

import cn.heckman.backend.mqlistener.TestListener;
import cn.heckman.moduleactivemq.consumer.MQConst;
import cn.heckman.moduleactivemq.consumer.MQConsumer;
import cn.heckman.modulecommon.utils.SpringContextUtils;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.jms.JMSException;

public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            ActiveMQConnectionFactory activeMQConnectionFactory = SpringContextUtils.getBean(ActiveMQConnectionFactory.class);
            ActiveMQConnection connection = (ActiveMQConnection) activeMQConnectionFactory.createConnection();
            new MQConsumer(MQConst.VISIT_LOG, new TestListener(), connection);
            new MQConsumer(MQConst.SMS_REQ, new TestListener(), connection);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
