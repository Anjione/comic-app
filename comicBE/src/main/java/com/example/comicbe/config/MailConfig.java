package com.example.comicbe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${email.host:}")
    private String host;

    @Value("${email.port:}")
    private Integer port;

    @Value("${email.username:}")
    private String username;

    @Value("${email.password:}")
    private String password;

    @Value("${email.mail.smtp.auth:}")
    private String auth;

    @Value("${email.mail.smtp.starttls.enable:}")
    private String starttlsEnable;

    @Value("${email.mail.transport.protocol:}")
    private String protocol;

    @Bean
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", protocol);
        props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", starttlsEnable);
        return javaMailSender;
    }

}