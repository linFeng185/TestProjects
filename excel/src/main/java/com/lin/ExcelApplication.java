package com.lin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

/**
 * @Author: lin
 * @Date: 2021/1/29 17:02
 */
@SpringBootApplication
public class ExcelApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(ExcelApplication.class, args);
        System.out.println(LocalDateTime.now()+"：启动成功");
    }
}
