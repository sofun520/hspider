package cn.heckman.backend;

import cn.heckman.backend.mqlistener.*;
import cn.heckman.backend.thread.InsertDataThread;
import cn.heckman.moduleactivemq.consumer.MQConst;
import cn.heckman.moduleactivemq.consumer.MQConsumer2;
import cn.heckman.modulecommon.utils.SpringContextUtils;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.jms.Connection;
import javax.jms.JMSException;

public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {

//    @Autowired
//    private ActiveMQConnectionFactory activeMQConnectionFactory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            ActiveMQConnectionFactory activeMQConnectionFactory = SpringContextUtils.getBean(ActiveMQConnectionFactory.class);
            Connection connection = activeMQConnectionFactory.createConnection();
            connection.start();
            new MQConsumer2(MQConst.VISIT_LOG, new VisitLogListener(), connection);
            new MQConsumer2(MQConst.SMS_REQ, new SmsREQListener(), connection);
            new MQConsumer2(MQConst.SMS_REPORT, new SmsReportListener(), connection);
            new MQConsumer2(MQConst.EMAIL_RESULT_INSERT, new EmailResultListener(), connection);
            new MQConsumer2(MQConst.SMS_MT, new SmsMTListener(), connection);
            new MQConsumer2(MQConst.SMS_P2P_REQ, new P2PSmsInsertListener(), connection);

            new InsertDataThread().init();

        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
