package cn.heckman.backend.scheduler;

import cn.heckman.modulecommon.utils.OkHttpUtils;
import org.springframework.stereotype.Component;

@Component
public class DataSync {

    /**
     * eureka apps data sync
     */
    public void eurekaAppSync() {
        OkHttpUtils.get("");
    }

}
