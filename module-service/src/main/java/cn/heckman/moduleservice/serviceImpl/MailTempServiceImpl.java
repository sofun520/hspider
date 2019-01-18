package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.modulemail.service.Mailservice;
import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.MailTempDao;
import cn.heckman.moduleservice.entity.MQTopic;
import cn.heckman.moduleservice.entity.MailResult;
import cn.heckman.moduleservice.entity.MailTemp;
import cn.heckman.moduleservice.service.MailTempService;
import cn.heckman.moduleservice.utils.HqlUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class MailTempServiceImpl extends AbstractService<MailTemp> implements MailTempService {

    @Autowired
    private MailTempDao mailTempDao;

    @Autowired
    private Mailservice mailservice;

    @Autowired
    private MQProducer producerService;

    @Override
    public BaseDaoInterface<MailTemp, Serializable> getDao() {
        return mailTempDao;
    }

    @Override
    public Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from MailTemp obj ";
        if (null != param.get("keyword")) {
            hql = HqlUtil.addCondition(hql, "keyword", param.get("keyword").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        if (null != param.get("temp")) {
            hql = HqlUtil.addCondition(hql, "temp", param.get("temp").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        return pageNDList(hql, pageNo, pageSize);
    }

    @Override
    public void sendTempSms(String id, String address, String from) throws Exception {
        MailTemp mailTemp = mailTempDao.findById(id).get();
        mailservice.sendHtmlMail("测试邮件", mailTemp.getTemp(), address, "", from);

        MailResult mailResult = new MailResult(address, "", mailTemp.getTemp());
//        Destination destination = new ActiveMQQueue(MQTopic.EMAIL_RESULT_INSERT);
        //推送到mq，从backend服务入库
        producerService.sendMessage(MQTopic.EMAIL_RESULT_INSERT, JSON.toJSONString(mailResult));
    }
}
