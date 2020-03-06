package com.companyname.projectname;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.companyname.projectname.dao")
public class ProjectnameApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectnameApplication.class, args);
    }

}
