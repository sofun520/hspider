package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.SmsGatewayTestDao;
import cn.heckman.moduleservice.entity.SmsGatewayTest;
import cn.heckman.moduleservice.service.SmsGatewayTestService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class SmsGatewayTestServiceImpl extends AbstractService<SmsGatewayTest> implements SmsGatewayTestService {

    @Autowired
    private SmsGatewayTestDao smsGatewayTestDao;

    @Override
    public BaseDaoInterface<SmsGatewayTest, Serializable> getDao() {
        return smsGatewayTestDao;
    }

    @Override
    public Page<SmsGatewayTest> getSended(int pageNo, int pageSize, String account) throws Exception {
        String hql = "from SmsGatewayTest obj ";
        hql = HqlUtil.addCondition(hql, "account", account);
        hql = HqlUtil.addCondition(hql, "status", 1);
        return pageList(hql, pageNo, pageSize);
    }

    @Override
    public void saveList(String sql) throws Exception {
        updateNativeHql(sql);
    }
}
