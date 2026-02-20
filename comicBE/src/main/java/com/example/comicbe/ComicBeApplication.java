package com.example.comicbe;

import com.example.comicbe.config.RsaKeyConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class ComicBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComicBeApplication.class, args);
    }

}
