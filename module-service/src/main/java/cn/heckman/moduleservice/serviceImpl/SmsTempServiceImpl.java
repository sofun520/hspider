package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.SmsTempDao;
import cn.heckman.moduleservice.entity.SmsTemp;
import cn.heckman.moduleservice.service.SmsTempService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class SmsTempServiceImpl extends AbstractService<SmsTemp> implements SmsTempService {

    @Autowired
    private SmsTempDao smsTempDao;

    @Override
    public BaseDaoInterface<SmsTemp, Serializable> getDao() {
        return smsTempDao;
    }

    @Override
    public Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from SmsTemp obj ";
        if (null != param.get("tempId")) {
            hql = HqlUtil.addCondition(hql, "tempId", param.get("tempId").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        if (null != param.get("tempContenmt")) {
            hql = HqlUtil.addCondition(hql, "tempContenmt", param.get("tempContenmt").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        return pageList(hql, pageNo, pageSize);
    }

}
