package com.kapok;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages = "com.kapok.dao")
public class RedisServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisServerApplication.class, args);
    }

}
