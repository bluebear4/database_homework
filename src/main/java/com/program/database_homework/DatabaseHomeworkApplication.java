package com.program.database_homework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication()
@MapperScan("com.program.database_homework.mapper")
public class DatabaseHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseHomeworkApplication.class, args);
    }

}
