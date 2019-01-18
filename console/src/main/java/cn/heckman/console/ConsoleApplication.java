package cn.heckman.console;

import cn.heckman.console.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaServer
@SpringBootApplication(scanBasePackages = "cn.heckman")
@RestController
@EnableCaching
@EnableTransactionManagement
public class ConsoleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        app.addListeners(new ApplicationInit());
        app.run(args);
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new AuthFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("authFilter");
        registration.setOrder(Integer.MAX_VALUE - 1);
        return registration;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ConsoleApplication.class);
    }

//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private MQProducer mqProducer;
//
//    @Autowired
//    private RoleService roleService;
//
//    @RequestMapping("/redis")
//    public String redisTest() {
//        redisService.set("a", "test");
//        System.out.println(redisService.get("a"));
//        return "{\"test\":\"name\"}";
//    }
//
//    @RequestMapping("/mq")
//    public String activemq() {
//        mqProducer.sendMessage("VISIT_LOG", "tset");
//        return "{\"test\":\"name\"}";
//    }
//
//    @RequestMapping("/service")
//    public String service() {
//        try {
//            Page page = roleService.queryPage(Maps.newHashMap(), 1, 10);
//            System.out.println(JSON.toJSONString(page));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return "{\"test\":\"name\"}";
//    }


}

