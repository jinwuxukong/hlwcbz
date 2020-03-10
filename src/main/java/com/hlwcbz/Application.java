package com.hlwcbz;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableSwagger2Doc
@EnableMethodCache(basePackages = "com.hlwcbz")
@EnableCreateCacheAnnotation
@SpringBootApplication
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
