package cn.heckman.backend2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Backend2Application {

    public static void main(String[] args) {
        SpringApplication.run(Backend2Application.class, args);
    }

}

