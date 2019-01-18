package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.SpIndexColumnDao;
import cn.heckman.moduleservice.entity.SpIndexColumn;
import cn.heckman.moduleservice.service.SpIndexColumnService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by heckman on 2018/8/4.
 */
@Service
public class SpIndexColumnServiceImpl extends AbstractService<SpIndexColumn> implements SpIndexColumnService {

    @Autowired
    private SpIndexColumnDao spIndexColumnDao;

    @Override
    public BaseDaoInterface<SpIndexColumn, Serializable> getDao() {
        return spIndexColumnDao;
    }

    @Override
    public Page queryPage(Integer pageNo, Integer pageSize, Map<String, Object> params) throws Exception {
        String hql = "from SpIndexColumn obj ";
        if (null != params.get("tenantId")) {
            hql = HqlUtil.addCondition(hql, "tenant.id", params.get("tenantId"));
        }
        if (null != params.get("type")) {
            hql = HqlUtil.addCondition(hql, "type", params.get("type"));
        }
        return pageList(hql, pageNo, pageSize);
    }
}
