package cn.heckman.backend.scheduler;

import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.modulecommon.utils.HttpUrl;
import cn.heckman.modulecommon.utils.OkHttpUtils;
import cn.heckman.moduleredis.service.RedisService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.entity.SmsGatewayAccount;
import cn.heckman.moduleservice.entity.SmsGatewayTest;
import cn.heckman.moduleservice.service.SmsGatewayAccountService;
import cn.heckman.moduleservice.service.SmsGatewayTestService;
import cn.heckman.moduleservice.service.TenantService;
import cn.heckman.moduleservice.vo.EurekaApp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Configurable
@EnableScheduling
public class DataSync {

    private Logger logger = LoggerFactory.getLogger(DataSync.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private SmsGatewayTestService smsGatewayTestService;

    @Autowired
    private SmsGatewayAccountService smsGatewayAccountService;

    @Autowired
    private TenantService tenantService;

    /**
     * eureka apps data sync
     */
    @Scheduled(fixedDelay = 30000)
    public void eurekaAppSync() {
        long startTime = System.currentTimeMillis();
        try {
            logger.info("===>同步eureka app数据到redis");
            String result = OkHttpUtils.get(HttpUrl.EUREKA_APP_URL);

            JSONObject json = JSONObject.parseObject(result);
            System.out.println(json.toJSONString());
            JSONArray application = json.getJSONObject("applications").getJSONArray("application");
            JSONObject instance = null;
            EurekaApp eurekaApp = null;
            List<EurekaApp> list = Lists.newArrayList();
            for (int i = 0; i < application.size(); i++) {
                instance = application.getJSONObject(i);

                eurekaApp = new EurekaApp(instance.getString("name"),
                        instance.getJSONArray("instance").getJSONObject(0).getString("ipAddr"),
                        instance.getJSONArray("instance").getJSONObject(0).getJSONObject("port").getString("$"),
                        instance.getJSONArray("instance").getJSONObject(0).getString("status"),
                        instance.getJSONArray("instance").getJSONObject(0).getString("healthCheckUrl"));
                list.add(eurekaApp);
            }

            redisService.set("eureka_app", JSON.toJSONString(list));
            logger.info("===>同步成功，耗时：{} ms", (System.currentTimeMillis() - startTime));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //每天执行一次
    @Scheduled(fixedDelay = 10000)
    public void push() throws Exception {
        int pageSize = 1000;
        String hql = "from SmsGatewayAccount obj ";
        List<SmsGatewayAccount> list = smsGatewayAccountService.getList(hql);
        for (SmsGatewayAccount account : list) {
            //账号有推送地址则推送
            if (StringUtils.isNotBlank(account.getReportUrl())) {
                Page<SmsGatewayTest> page = smsGatewayTestService.getSended(1, pageSize, account.getAccount());
                int pageNo = 1;
                for (int i = 1; i <= page.getTotalPageCount(); i++) {
                    page = smsGatewayTestService.getSended(i, pageSize, account.getAccount());

                    List<SmsGatewayTest> ts = page.getResult();
                    Map<String, Object> map = new HashMap<>();
                    map.put("list", ts);
                    OkHttpUtils.postJson(account.getReportUrl(), map);

                    StringBuilder sb = new StringBuilder();
                    for (SmsGatewayTest sms : ts) {
                        sb.append("'" + sms.getId() + "',");
                    }
                    String ids = sb.substring(0, sb.length() - 1);
                    String sql = "update sms_gateway_test set status=2 where id in (" + ids + ")";
                    smsGatewayTestService.updateNativeHql(sql);
                }
            }
        }
    }

    //每天执行一次
    @Scheduled(cron = "0 30 0 1/1 * ?")
    public void reportCurrentByCron() {
        logger.info("==统计每天控制台访问量开始==");
        long start = System.currentTimeMillis();
        String startTime = DateUtils.getYestdayBaseToday();
        String endTime = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
        String sql = "INSERT INTO tb_visit_statistic SELECT " +
                " REPLACE (UUID(), '-', '') AS id, " +
                " tv.tenant_id, " +
                " tt.tenant_name, " +
                " count(tv.tenant_id) AS total, " +
                " date(tv.create_dt) AS date, " +
                " tv.platform_type, " +
                " NOW() AS create_dt, " +
                " NOW() AS last_modi_dt, " +
                " 0 AS deleted, " +
                " 0 AS sortno, " +
                " 0 AS version " +
                "FROM " +
                " `tb_visit_log` tv, " +
                " tb_tenant tt " +
                "WHERE " +
                " tv.tenant_id = tt.id " +
                "AND tv.tenant_id != '' " +
                "AND tv.tenant_id IS NOT NULL " +
                "AND tv.platform_type = 1 " +
                "AND date(tv.create_dt) >= '" + startTime + "' " +
                "AND date(tv.create_dt) < '" + endTime + "' " +
                "GROUP BY " +
                " tv.tenant_id " +
                "UNION " +
                " SELECT " +
                "  REPLACE (UUID(), '-', '') AS id, " +
                "  tt.id, " +
                "  tt.tenant_name, " +
                "  0 AS total, " +
                "  '" + endTime + "' AS date, " +
                "  1 AS platform_type, " +
                "  NOW() AS create_dt, " +
                "  NOW() AS last_modi_dt, " +
                "  0 AS deleted, " +
                "  0 AS sortno, " +
                "  0 AS version " +
                " FROM " +
                "  tb_tenant tt " +
                " WHERE " +
                "  tt.id NOT IN ( " +
                "   SELECT " +
                "    tv.tenant_id " +
                "   FROM " +
                "    `tb_visit_log` tv " +
                "   WHERE " +
                "    tv.tenant_id != '' " +
                "   AND tv.tenant_id IS NOT NULL " +
                "   AND tv.platform_type = 1 " +
                "   AND date(tv.create_dt) >= '" + startTime + "' " +
                "   AND date(tv.create_dt) < '" + endTime + "' " +
                "   GROUP BY " +
                "    tv.tenant_id " +
                "  )";
        tenantService.executeSql(sql);
        long end = System.currentTimeMillis();
        logger.info("==统计每天控制台访问量结束==");
        logger.info("==共使用" + ((end - start) / 1000) + "s==");
    }

}
