package cn.heckman.backend.scheduler;

import cn.heckman.modulecommon.utils.HttpUrl;
import cn.heckman.modulecommon.utils.OkHttpUtils;
import cn.heckman.moduleredis.service.RedisService;
import cn.heckman.moduleservice.vo.EurekaApp;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.EAN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configurable
@EnableScheduling
public class DataSync {

    private Logger logger = LoggerFactory.getLogger(DataSync.class);

    @Autowired
    private RedisService redisService;

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

}
