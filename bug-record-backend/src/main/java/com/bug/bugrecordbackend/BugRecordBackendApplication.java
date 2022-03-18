package com.bug.bugrecordbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.bug.bugrecordbackend.mapper")
@EnableCaching
public class BugRecordBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BugRecordBackendApplication.class, args);
    }

}
