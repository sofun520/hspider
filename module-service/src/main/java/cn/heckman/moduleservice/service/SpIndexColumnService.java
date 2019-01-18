package cn.heckman.moduleservice.service;

import cn.heckman.moduleservice.base.BaseService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.SpIndexColumn;

import java.util.Map;

/**
 * Created by heckman on 2018/8/4.
 */
public interface SpIndexColumnService extends BaseService<SpIndexColumn> {
    Page queryPage(Integer pageNo, Integer pageSize, Map<String, Object> params) throws Exception;
}
