package cn.heckman.console.config;

import cn.heckman.moduleservice.ServiceApiConfig;
import cn.heckman.moduleservice.ServiceConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Tandy on 2016/6/7.
 */
@ComponentScan({"cn.heckman"})
@EnableWebMvc
@Configuration
@PropertySource("classpath:/application.properties")
@Import({
        RedisAutoConfiguration.class,
        ServiceConfig.class,
        ServiceApiConfig.class
})
@EnableJpaRepositories(value = {"cn.heckman"})
@EnableAutoConfiguration
public class SpringStartupConfig {
    @Bean
    public String getSystemId() {
        return "app.oa";
    }
}
