package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.SmsGatewayTest;

public interface SmsGatewayTestService extends BaseService<SmsGatewayTest> {
    Page<SmsGatewayTest> getSended(int pageNo, int pageSize, String account) throws Exception;

    void saveList(String sql) throws Exception;
}
