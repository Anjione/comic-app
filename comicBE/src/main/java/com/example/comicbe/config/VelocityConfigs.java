package com.example.comicbe.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@Slf4j
@Configurable
public class VelocityConfigs {
    @Bean("velocityEngine")
    public VelocityEngine getVelocityEngine() {

        VelocityEngine ve = new VelocityEngine();

        Properties p = new Properties();
        p.put("velocimacro.permissions.allow.inline.local.scope", "true");
        p.put("velocimacro.library.autoreload", "true");
        p.put("velocimacro.context.localscope", "true");
        ve.setProperty("runtime.log.logsystem.log4j.logger", "root");

        ve.init(p);
        return ve;
    }

}