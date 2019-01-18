package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.TsaOilDao;
import cn.heckman.moduleservice.entity.TsaOil;
import cn.heckman.moduleservice.service.TsaOilService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class TsaOilServiceImpl extends AbstractService<TsaOil> implements TsaOilService {

    @Autowired
    private TsaOilDao tsaOilDao;

    @Override
    public BaseDaoInterface<TsaOil, Serializable> getDao() {
        return tsaOilDao;
    }

    @Override
    public Page queryPage(Map<String, Object> map, int pageNo, int pageSize) throws Exception {
        String hql = "from TsaOil obj ";
        if(null!=map.get("nickname")){
            hql = HqlUtil.addCondition(hql,"wxUser.nickname",map.get("nickname"));
        }
        Page page = pageList(hql, pageNo, pageSize);
        return page;
    }
}
