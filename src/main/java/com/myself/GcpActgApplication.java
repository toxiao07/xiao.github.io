package com.myself;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan({"com.myself.modules.*.dao"})
@EnableTransactionManagement
@ServletComponentScan
public class GcpActgApplication {

    public static void main(String[] args) {
        SpringApplication.run(GcpActgApplication.class, args);
    }

}
