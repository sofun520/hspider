package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.TsaOil;

import java.util.Map;

public interface TsaOilService extends BaseService<TsaOil> {

    Page queryPage(Map<String, Object> map, int pageNo, int pageSize) throws Exception;

}
