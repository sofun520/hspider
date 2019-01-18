package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.MailTemp;

import java.util.Map;

public interface MailTempService extends BaseService<MailTemp> {
    Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    void sendTempSms(String id, String address, String from) throws Exception;
}
