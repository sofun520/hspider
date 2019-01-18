package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.DataDictionary;

import java.util.Map;

/**
 * Created by heckman on 2018/6/30.
 */
public interface DataDictionaryService extends BaseService<DataDictionary> {
    Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
}
