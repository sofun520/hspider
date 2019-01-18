package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.SmsGatewayAccountDao;
import cn.heckman.moduleservice.entity.SmsGatewayAccount;
import cn.heckman.moduleservice.service.SmsGatewayAccountService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class SmsGatewayAccountServiceImpl extends AbstractService<SmsGatewayAccount> implements SmsGatewayAccountService {

    @Autowired
    private SmsGatewayAccountDao smsGatewayAccountDao;

    @Override
    public BaseDaoInterface<SmsGatewayAccount, Serializable> getDao() {
        return smsGatewayAccountDao;
    }

    @Override
    public List<SmsGatewayAccount> checkaccount(String account, String userId, String password) throws Exception {
        String hql = "from SmsGatewayAccount obj ";
        hql = HqlUtil.addCondition(hql, "account", account);
        hql = HqlUtil.addCondition(hql, "userid", userId);
        hql = HqlUtil.addCondition(hql, "password", password);
        return getList(hql);
    }

    @Override
    public Page queryPage(Integer pageNo, Integer pageSize, Map<String, String> map) throws Exception {
        String hql = "from SmsGatewayAccount obj ";
        if (null != map.get("account")) {
            hql = HqlUtil.addCondition(hql, "account", map.get("account"));
        }
        if (null != map.get("userId")) {
            hql = HqlUtil.addCondition(hql, "userid", map.get("userId"));
        }
        if (null != map.get("name")) {
            hql = HqlUtil.addCondition(hql, "name", map.get("name"));
        }
        hql = HqlUtil.addOrder(hql, "createTime", "desc");
        return pageList(hql, pageNo, pageSize);
    }
}
