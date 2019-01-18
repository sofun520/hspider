package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.dao.TsaIndexDao;
import cn.heckman.moduleservice.entity.TsaIndex;
import cn.heckman.moduleservice.service.TsaIndexService;
import cn.heckman.moduleservice.utils.HqlUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class TsaIndexServiceImpl extends AbstractService<TsaIndex> implements TsaIndexService {

    @Autowired
    private TsaIndexDao tsaIndexDao;

    @Override
    public BaseDaoInterface<TsaIndex, Serializable> getDao() {
        return tsaIndexDao;
    }

    @Override
    public RestResponse show(String tenantCode) throws Exception {
        RestResponse restResponse = new RestResponse();
        String hql = null;
        List<TsaIndex> list = null;
        JSONObject obj = new JSONObject();
        for (int i = 0; i < 4; i++) {
            hql = "from TsaIndex obj ";
            hql = HqlUtil.addCondition(hql, "tenant.tenantCode", tenantCode);
            hql = HqlUtil.addCondition(hql, "location", (i + 1));
            list = getList(hql);
            obj.put((i + 1) + "", list);
        }
        restResponse.setData(obj);
        restResponse.setSuccess(true);
        return restResponse;
    }
}
