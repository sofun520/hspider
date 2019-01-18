package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.DataItemDao;
import cn.heckman.moduleservice.entity.DataItem;
import cn.heckman.moduleservice.service.DataItemService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by heckman on 2018/6/30.
 */
@Service
public class DataItemServiceImpl extends AbstractService<DataItem> implements DataItemService {

    @Autowired
    private DataItemDao dataItemDao;

    @Override
    public BaseDaoInterface<DataItem, Serializable> getDao() {
        return dataItemDao;
    }

    @Override
    public Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from DataItem obj ";
        if (null != param.get("dictionaryId")) {
            hql = HqlUtil.addCondition(hql, "dataDictionary.id", param.get("dictionaryId"));
        }
        return pageList(hql, pageNo, pageSize);
    }

    @Override
    public List queryList(Map<String, Object> param) throws Exception {
        String hql = "from DataItem obj ";
        if (null != param.get("dictionaryId")) {
            hql = HqlUtil.addCondition(hql, "dataDictionary.id", param.get("dictionaryId"));
        }
        return list(hql);
    }
}
