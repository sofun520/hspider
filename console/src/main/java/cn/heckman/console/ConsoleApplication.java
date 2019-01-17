package cn.heckman.console;

import cn.heckman.moduleactivemq.consumer.MQProducer;
import cn.heckman.moduleredis.service.RedisService;
import cn.heckman.moduleservice.base.Page;
import cn.heckman.moduleservice.service.RoleService;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.MappedSuperclass;

@EnableEurekaServer
@SpringBootApplication(scanBasePackages = "cn.heckman")
@RestController
public class ConsoleApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        app.addListeners(new ApplicationInit());
        app.run(args);
    }

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQProducer mqProducer;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/redis")
    public String redisTest() {
        redisService.set("a", "test");
        System.out.println(redisService.get("a"));
        return "{\"test\":\"name\"}";
    }

    @RequestMapping("/mq")
    public String activemq() {
        mqProducer.sendMessage("VISIT_LOG", "tset");
        return "{\"test\":\"name\"}";
    }

    @RequestMapping("/service")
    public String service() {
        try {
            Page page = roleService.queryPage(Maps.newHashMap(), 1, 10);
            System.out.println(JSON.toJSONString(page));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "{\"test\":\"name\"}";
    }


}

