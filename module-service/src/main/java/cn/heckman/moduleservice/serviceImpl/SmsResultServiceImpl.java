package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.dao.SmsResultDao;
import cn.heckman.moduleservice.entity.SmsResult;
import cn.heckman.moduleservice.service.SmsResultService;
import cn.heckman.moduleservice.utils.HqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service
public class SmsResultServiceImpl extends AbstractService<SmsResult> implements SmsResultService {

    @Autowired
    private SmsResultDao smsResultDao;

    @Override
    public BaseDaoInterface<SmsResult, Serializable> getDao() {
        return smsResultDao;
    }

    @Override
    public void updateByPhoneSid(String status, String errMsg, String mobile, String sid) {
//        String hql = "update SmsResult obj set obj.deliverCode='" + status + "',set obj.deliverMsg='" + errMsg + "' ";
//        hql = HqlUtil.addCondition(hql, "phone", mobile);
//        hql = HqlUtil.addCondition(hql, "taskId", sid);
//        executeHql(hql);

        String sql = "update tb_sms_result obj " +
                "set obj.deliver_code = '" + status + "'," +
                "obj.deliver_msg='" + errMsg + "' where obj.phone='" + mobile + "' and obj.task_id='" + sid + "'";
        updateNativeHql(sql);
    }

    @Override
    public Page queryPage(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        String hql = "from SmsResult obj ";
        if (null != param.get("phone")) {
            hql = HqlUtil.addCondition(hql, "phone", param.get("phone").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        if (null != param.get("message")) {
            hql = HqlUtil.addCondition(hql, "message", param.get("message").toString().trim(), HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING_LIKE, "");
        }
        hql = HqlUtil.addOrder(hql, "createTime", "desc");
        return pageList(hql, pageNo, pageSize);
    }
}
