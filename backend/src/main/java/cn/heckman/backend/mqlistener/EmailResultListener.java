package cn.heckman.backend.mqlistener;

import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.moduleservice.entity.MailResult;
import cn.heckman.moduleservice.service.MailResultService;
import com.alibaba.fastjson.JSON;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class EmailResultListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage){
                TextMessage text = (TextMessage)message;
                MailResultService mailResultService = SpringContextUtils.getBean(MailResultService.class);
                MailResult mailResult = JSON.parseObject(text.getText(), MailResult.class);
                mailResultService.save(mailResult);
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
