package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.dao.WxUserDao;
import cn.heckman.moduleservice.entity.WxUser;
import cn.heckman.moduleservice.service.WxUserService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class WxUserServiceImpl extends AbstractService<WxUser> implements WxUserService {

    @Autowired
    private WxUserDao wxUserDao;

    @Override
    public BaseDaoInterface<WxUser, Serializable> getDao() {
        return wxUserDao;
    }

    @Override
    public WxUser getWxUser(String openid) throws Exception {
        String hql = "from WxUser obj ";
        hql = HqlUtil.addCondition(hql, "openId", openid);
        List<WxUser> list = getList(hql);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
