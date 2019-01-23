package cn.heckman.backend.mqlistener;

import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.moduleservice.service.SmsResultService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SmsReportListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            SmsResultService smsResultService = SpringContextUtils.getBean(SmsResultService.class);
            if (message instanceof TextMessage) {
                TextMessage text = (TextMessage) message;

                JSONArray array = JSON.parseArray(text.getText());
                JSONObject object = null;
                for (int i = 0; i < array.size(); i++) {
                    object = array.getJSONObject(i);
                    smsResultService.updateByPhoneSid(object.getString("report_status") + "",
                            object.getString("error_msg") + "",
                            object.getString("mobile"),
                            object.getString("sid"));
                }
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
