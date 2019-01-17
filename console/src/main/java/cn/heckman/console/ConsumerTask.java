//package cn.heckman.console;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//@Component
//public class ConsumerTask {
//
//    private Logger logger = LoggerFactory.getLogger(ConsumerTask.class);
//
//    @JmsListener(destination = "VISIT_LOG", containerFactory = "jmsQueueListener")
//    public void operatorConsumer(final TextMessage text, Session session) {
//        try {
//
//            logger.info("日志持久化：{}", text.getText());
//            text.acknowledge();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            try {
//                session.recover();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
