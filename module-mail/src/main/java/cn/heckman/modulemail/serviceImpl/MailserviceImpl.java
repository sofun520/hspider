package cn.heckman.modulemail.serviceImpl;

import cn.heckman.modulemail.service.Mailservice;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@SuppressWarnings("all")
@Service
public class MailserviceImpl implements Mailservice {

    private Logger logger = LoggerFactory.getLogger(MailserviceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendMail(String title, String content, String receiver, String cc, String from) {
        try {
            SimpleMailMessage mainMessage = new SimpleMailMessage();
            mainMessage.setFrom(from);
            //接收者
            mainMessage.setTo(receiver);
            if (StringUtils.isNotBlank(cc)) {
                mainMessage.setCc(cc);
            }
            //发送的标题
            mainMessage.setSubject(title);
            //发送的内容
            mainMessage.setText(content);
            javaMailSender.send(mainMessage);
            return true;
        } catch (Exception ex) {
            logger.error("发送邮件出错：", ex);
        }
        return false;
    }

//    @Override
//    public boolean sendMail(String title, String content, String receiver, String cc, String from) {
//        try {
//            JavaMailSenderImpl javaMailSender= new JavaMailSenderImpl();
//            javaMailSender.setHost(ConstUtils.getStr("spring.mail.host"));//链接服务器
//            javaMailSender.setPort(ConstUtils.getInt("spring.mail.port",465));
//            javaMailSender.setUsername(ConstUtils.getStr("spring.mail.username"));//账号
//            javaMailSender.setPassword(ConstUtils.getStr("spring.mail.password"));//密码
//            javaMailSender.setDefaultEncoding("UTF-8");
//
//            Properties properties = new Properties();
//            properties.setProperty("mail.smtp.auth", "true");//开启认证
//            properties.setProperty("mail.debug", "true");//启用调试
//            properties.setProperty("mail.smtp.timeout", "1000");//设置链接超时
//            properties.setProperty("mail.smtp.port", Integer.toString(ConstUtils.getInt("spring.mail.port",465)));//设置端口
//            properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(ConstUtils.getInt("spring.mail.port",465)));//设置ssl端口
//            properties.setProperty("mail.smtp.socketFactory.fallback", "false");
//            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            javaMailSender.setJavaMailProperties(properties);
//
//            SimpleMailMessage mainMessage = new SimpleMailMessage();
//            mainMessage.setFrom(from);
//            //接收者
//            mainMessage.setTo(receiver);
//            if (StringUtils.isNotBlank(cc)) {
//                mainMessage.setCc(cc);
//            }
//            //发送的标题
//            mainMessage.setSubject(title);
//            //发送的内容
//            mainMessage.setText(content);
//            javaMailSender.send(mainMessage);
//            return true;
//        }catch (Exception ex) {
//            logger.error("发送邮件出错：", ex);
//        }
//        return false;
//    }

    @Override
    public boolean sendHtmlMail(String title, String content, String receiver, String cc, String from) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            //建立邮件消息
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //发送者
            helper.setFrom(from);
            //接收者
            helper.setTo(receiver);
            if (StringUtils.isNotBlank(cc)) {
                helper.setCc(cc);
            }
            //发送的标题
            helper.setSubject(title);
            //发送的内容
            helper.setText(content, true);
            javaMailSender.send(message);
            return true;
        } catch (Exception ex) {
            logger.error("发送邮件出错：", ex);
        }
        return false;
    }
}
