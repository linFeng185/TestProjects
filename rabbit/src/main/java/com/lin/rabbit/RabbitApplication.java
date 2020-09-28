package com.lin.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: linFeng
 * @Date: 2020/9/28 14:15
 *
 */
@SpringBootApplication
public class RabbitApplication {
    public static void main(String[] args) {
        System.out.println("--启动--");
        SpringApplication.run(RabbitApplication.class, args);
    }
}
