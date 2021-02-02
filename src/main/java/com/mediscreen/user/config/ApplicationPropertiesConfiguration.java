package com.mediscreen.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("my-configs")
public class ApplicationPropertiesConfiguration {
    private int maconfig;

    public int getMaconfig() {
        return maconfig;
    }

    public void setMaconfig(int maconfig) {
        this.maconfig = maconfig;
    }
}
