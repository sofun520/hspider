package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.VisitLog;

import java.util.Map;

public interface VisitLogService extends BaseService<VisitLog> {

    Page<VisitLog> page(Map<String, Object> map, Integer pageNo, Integer pageSize) throws Exception;

}
