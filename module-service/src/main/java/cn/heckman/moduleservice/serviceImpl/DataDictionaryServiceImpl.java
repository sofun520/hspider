package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.DataDictionaryDao;
import cn.heckman.moduleservice.entity.DataDictionary;
import cn.heckman.moduleservice.service.DataDictionaryService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by heckman on 2018/6/30.
 */
@Service
public class DataDictionaryServiceImpl extends AbstractService<DataDictionary> implements DataDictionaryService {

    @Autowired
    private DataDictionaryDao dataDictionaryDao;

    @Override
    public BaseDaoInterface<DataDictionary, Serializable> getDao() {
        return dataDictionaryDao;
    }

    @Override
    public Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from DataDictionary obj ";
        if (null != param.get("code")) {
            hql = HqlUtil.addCondition(hql, "code", param.get("code"));
        }
        return pageList(hql, pageNo, pageSize);
    }
}
