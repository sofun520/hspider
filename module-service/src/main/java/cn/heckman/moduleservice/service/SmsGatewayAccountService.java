package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.SmsGatewayAccount;

import java.util.List;
import java.util.Map;

public interface SmsGatewayAccountService extends BaseService<SmsGatewayAccount> {
    List<SmsGatewayAccount> checkaccount(String account, String userId, String password) throws Exception;

    Page queryPage(Integer pageNo, Integer pageSize, Map<String, String> map) throws Exception;
}
