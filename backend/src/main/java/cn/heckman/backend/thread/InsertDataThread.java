package cn.heckman.backend.thread;

import cn.heckman.backend.mqlistener.SmsMTListener;
import cn.heckman.modulecommon.utils.ConstUtils;
import cn.heckman.modulecommon.utils.SpringContextUtils;
import cn.heckman.modulecommon.utils.UUIDGenerator;
import cn.heckman.moduleservice.entity.SmsGatewayTest;
import cn.heckman.moduleservice.service.SmsGatewayTestService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class InsertDataThread {

    private Logger logger = LoggerFactory.getLogger(InsertDataThread.class);
    public static long startTime = 0l;
    public static long endTime = System.currentTimeMillis();
    public static long zubaoTime = ConstUtils.getLong("zubao.time", 10);
    public static int getSize = 0;

    private final ScheduledExecutorService scheduledExecutorService = Executors
            .newSingleThreadScheduledExecutor(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "GateWaySubmitStatusThread");
                }
            });


    public void init() {
        SmsGatewayTestService smsGatewayTestService = SpringContextUtils.getBean(SmsGatewayTestService.class);
        int zubaoSize = ConstUtils.getInt("zubao.size");

        startTime = System.currentTimeMillis();

        this.scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    int queueSize = SmsMTListener.queue.size();
                    boolean flag = false;

                    logger.info("mq队列拉取信息：{}条，拉取速度：{}条/s，耗时{}s", queueSize, queueSize - getSize < 0 ? 0 : (queueSize - getSize) / 2, (ConstUtils.getLong("zubao.time", 10) - zubaoTime) / 1000);

                    if (queueSize > zubaoSize) {
                        //达到组包大小，调用保存方法
                        logger.info("组包达到数量==>保存");
                        flag = true;
                        insert(flag);
                        //重置开始时间
                        zubaoTime = ConstUtils.getLong("zubao.time", 10);
                    } else if (zubaoTime <= 0 && queueSize > 0) {
                        //组包时间到，调用保存方法
                        logger.info("超过组包时长=>保存，持续时间：" + (ConstUtils.getLong("zubao.time", 10) - zubaoTime));
                        flag = false;
                        insert(flag);
                        //重置开始时间
                        zubaoTime = ConstUtils.getLong("zubao.time", 10);
                    }

                    zubaoTime -= 2000;
                    getSize = queueSize;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 1000 * 1, 2000, TimeUnit.MILLISECONDS);
    }

    public void insert(boolean flag) {
        try {
            SmsGatewayTestService smsGatewayTestService = SpringContextUtils.getBean(SmsGatewayTestService.class);
            int zubaoSize = ConstUtils.getInt("zubao.size");
            StringBuilder sb = new StringBuilder("INSERT INTO `sms_gateway_test` (`id`,`account`, `task_id`, `phone`, `status`, `create_dt`, `last_modi_dt`, `sortno`, `deleted`, `version`) values ");
            String a = null;
            SmsGatewayTest smsGatewayTest = null;

            int size = 0;
            if (flag) {
                size = zubaoSize;
            } else {
                size = SmsMTListener.queue.size();
            }

            for (int i = 0; i < size; i++) {
                String uuid = UUIDGenerator.uuid();
                a = SmsMTListener.queue.poll();
                smsGatewayTest = JSON.parseObject(a, SmsGatewayTest.class);
//                System.out.println(JSON.toJSONString(smsGatewayTest));
                if (null != smsGatewayTest) {
                    sb.append("('" + uuid + "','" + smsGatewayTest.getAccount() + "','" + smsGatewayTest.getTaskId() + "','" + smsGatewayTest.getPhone() + "','1',now(),now(),0,0,0),");
                }
            }

            String sql = sb.substring(0, sb.length() - 1);
            logger.info("phone入库");
//            logger.info("sql => {}", sql);
            smsGatewayTestService.saveList(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
