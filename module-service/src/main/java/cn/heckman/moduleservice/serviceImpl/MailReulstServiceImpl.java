package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.MailResultDao;
import cn.heckman.moduleservice.entity.MailResult;
import cn.heckman.moduleservice.service.MailResultService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class MailReulstServiceImpl extends AbstractService<MailResult> implements MailResultService {

    @Autowired
    private MailResultDao mailResultDao;

    @Override
    public BaseDaoInterface<MailResult, Serializable> getDao() {
        return mailResultDao;
    }

    @Override
    public Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from MailResult obj ";
        if (null != param.get("address")) {
            hql = HqlUtil.addCondition(hql, "address", param.get("address").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        if (null != param.get("content")) {
            hql = HqlUtil.addCondition(hql, "content", param.get("content").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        return pageList(hql, pageNo, pageSize);
    }
}
