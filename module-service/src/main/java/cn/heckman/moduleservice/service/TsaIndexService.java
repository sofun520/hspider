package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.entity.TsaIndex;

public interface TsaIndexService extends BaseService<TsaIndex> {
    RestResponse show(String tenantCode) throws Exception;
}
