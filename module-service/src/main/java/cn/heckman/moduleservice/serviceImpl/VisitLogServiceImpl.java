package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.VisitLogDao;
import cn.heckman.moduleservice.entity.VisitLog;
import cn.heckman.moduleservice.service.VisitLogService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class VisitLogServiceImpl extends AbstractService<VisitLog> implements VisitLogService {

    @Autowired
    private VisitLogDao visitLogDao;

    @Override
    public BaseDaoInterface<VisitLog, Serializable> getDao() {
        return visitLogDao;
    }

    @Override
    public Page<VisitLog> page(Map<String, Object> map, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from VisitLog obj ";
        if (null != map.get("username")) {
            hql = HqlUtil.addCondition(hql, "username", map.get("username").toString());
        }
        if (null != map.get("startTime")) {
            hql = HqlUtil.addCondition(hql, "createTime", map.get("startTime"), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING, HqlUtil.COMPARECHAR_GREAT_EQ);
        }
        if (null != map.get("endTime")) {
            String endTime = DateUtils.addTime(map.get("endTime").toString(), "yyyy-MM-dd", 1);
            hql = HqlUtil.addCondition(hql, "createTime", endTime, HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING, HqlUtil.COMPARECHAR_LESS);
        }
        return pageList(hql, pageNo, pageSize);
    }
}
