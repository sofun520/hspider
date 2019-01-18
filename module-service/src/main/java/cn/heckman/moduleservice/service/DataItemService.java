package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.DataItem;

import java.util.List;
import java.util.Map;

/**
 * Created by heckman on 2018/6/30.
 */
public interface DataItemService extends BaseService<DataItem> {
    Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;

    List queryList(Map<String, Object> param) throws Exception;
}
