package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.MailResult;

import java.util.Map;

public interface MailResultService extends BaseService<MailResult> {

    Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
