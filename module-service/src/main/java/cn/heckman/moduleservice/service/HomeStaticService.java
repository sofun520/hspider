package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.RestResponse;

/**
 * Created by heckman on 2018/10/1.
 */
public interface HomeStaticService{
    RestResponse visitStatistic(String tenantCode, String tenantId) throws Exception;
}
