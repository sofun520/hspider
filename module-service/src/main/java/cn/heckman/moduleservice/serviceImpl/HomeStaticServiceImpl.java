package cn.heckman.moduleservice.serviceImpl;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleredis.service.RedisService;
import cn.heckman.moduleservice.base.AbstractService;
import cn.heckman.moduleservice.base.BaseDaoInterface;
import cn.heckman.moduleservice.base.RestResponse;
import cn.heckman.moduleservice.dao.TenantDao;
import cn.heckman.moduleservice.entity.Tenant;
import cn.heckman.moduleservice.service.HomeStaticService;
import cn.heckman.moduleservice.vo.HomeVisitStatisticVo;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heckman on 2018/10/1.
 */
@Service
public class HomeStaticServiceImpl extends AbstractService implements HomeStaticService {

    @Autowired
    private TenantDao tenantDao;

    @Override
    public BaseDaoInterface getDao() {
        return tenantDao;
    }

    @Autowired
    private RedisService redisService;

    @Override
    public RestResponse visitStatistic(String tenantCode, String tenantId) throws Exception {
        Date date = new Date();
        String startDate = DateUtils.getLastWeekMondayDate(date);
        String endDate = DateUtils.getLastWeekSundayDate();
        RestResponse restResponse = null;
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isNotBlank(tenantCode)) {
            String sql = "SELECT " +
                    " tenant_id as tenantId, " +
                    " tenant_name as tenantName, " +
                    " total, " +
                    " date " +
                    "FROM " +
                    " `tb_visit_statistic` " +
                    "WHERE " +
                    " tenant_id = '" + tenantId + "' " +
                    "AND date >= '" + startDate + "' " +
                    "AND date <= '" + endDate + "' " +
                    "ORDER BY " +
                    " date";
            List list = executNativeQuery(sql, HomeVisitStatisticVo.class);
            result.put("type", "tenant");
            result.put("list", list);
            restResponse = RestResponse.success(result);
            redisService.set(tenantCode + ":" + startDate, restResponse);
        } else {
            result.put("type", "admin");
            List<Tenant> ll = getList("from Tenant obj where obj.deleted=0 ");
            String sql = null;
            List list = null;
            JSONArray ids = new JSONArray();
            JSONArray values = new JSONArray();
            for (Tenant tt : ll) {
                sql = "SELECT " +
                        " tenant_id as tenantId, " +
                        " tenant_name as tenantName, " +
                        " total, " +
                        " date " +
                        "FROM " +
                        " `tb_visit_statistic` " +
                        "WHERE " +
                        " tenant_id = '" + tt.getId() + "' " +
                        "AND date >= '" + startDate + "' " +
                        "AND date <= '" + endDate + "' " +
                        "ORDER BY " +
                        " date";
                list = executNativeQuery(sql, HomeVisitStatisticVo.class);
                ids.add(tt.getTenantName());
                values.add(list);
            }
            result.put("ids", ids);
            result.put("values", values);
            restResponse = RestResponse.success(result);
            redisService.set("admin" + ":" + startDate, restResponse);
        }
        return restResponse;
    }
}
