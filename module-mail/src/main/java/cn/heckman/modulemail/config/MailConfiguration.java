package cn.heckman.modulemail.config;

import cn.heckman.modulecommon.utils.ConstUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

    @Bean
    public JavaMailSenderImpl JavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(ConstUtils.getStr("spring.mail.host"));
        mailSender.setUsername(ConstUtils.getStr("spring.mail.username"));
        mailSender.setPassword(ConstUtils.getStr("spring.mail.password"));
        return mailSender;
    }

}
