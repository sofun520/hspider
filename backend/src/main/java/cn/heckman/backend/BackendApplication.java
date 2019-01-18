package cn.heckman.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "cn.heckman")
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BackendApplication.class);
        app.addListeners(new ApplicationInit());
        app.run(args);
    }

}

