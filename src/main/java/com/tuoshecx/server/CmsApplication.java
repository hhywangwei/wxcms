package com.tuoshecx.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CmsApplication {

    public static void main(String[] args){
        new SpringApplicationBuilder(CmsApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
