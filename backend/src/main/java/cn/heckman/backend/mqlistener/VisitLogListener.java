package cn.heckman.backend.mqlistener;

import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.moduleservice.entity.VisitLog;
import cn.heckman.moduleservice.service.VisitLogService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class VisitLogListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(VisitLogListener.class);

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage text = (TextMessage) message;
                logger.info("日志持久化：{}", text.getText());
                VisitLogService visitLogService = SpringContextUtils.getBean(VisitLogService.class);
                visitLogService.save(JSON.parseObject(text.getText(), VisitLog.class));
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
