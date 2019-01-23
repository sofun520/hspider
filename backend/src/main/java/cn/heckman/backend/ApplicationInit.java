package cn.heckman.backend;

import cn.heckman.backend.mqlistener.*;
import cn.heckman.backend.thread.InsertDataThread;
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
            new MQConsumer(MQConst.VISIT_LOG, new VisitLogListener(), connection);
            new MQConsumer(MQConst.SMS_REQ, new SmsREQListener(), connection);
            new MQConsumer(MQConst.SMS_REPORT, new SmsReportListener(), connection);
            new MQConsumer(MQConst.EMAIL_RESULT_INSERT, new EmailResultListener(), connection);
            new MQConsumer(MQConst.SMS_MT, new SmsMTListener(), connection);

            new InsertDataThread().init();

        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
