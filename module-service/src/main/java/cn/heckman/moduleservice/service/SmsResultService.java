package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.SmsResult;

import java.util.Map;

public interface SmsResultService extends BaseService<SmsResult> {


    void updateByPhoneSid(String status, String errMsg, String mobile, String sid);

    Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
