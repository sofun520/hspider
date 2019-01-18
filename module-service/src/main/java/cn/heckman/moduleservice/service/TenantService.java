package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.Tenant;

import java.util.Map;

public interface TenantService extends BaseService<Tenant> {
    Tenant getByTenantCode(String tenantCode) throws Exception;

    Page queryPage(Integer pageNo, Integer pageSize, Map<String, Object> params) throws Exception;
}
