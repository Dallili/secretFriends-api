package org.dallili.secretfriends.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.swing.*;
import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${mail.host}")
    private String mailServerHost;
    @Value("${mail.port}")
    private int mailServerPort;
    @Value("${mail.username}")
    private String mailServerUsername;
    @Value("${mail.password}")
    private String mailServerPassword;

    //이메일 발송에 사용되는 JavaMailSender 빈으로 등록
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailServerHost);
        javaMailSender.setPort(mailServerPort);
        javaMailSender.setUsername(mailServerUsername);
        javaMailSender.setPassword(mailServerPassword);

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");

        return javaMailSender;

    }

}
