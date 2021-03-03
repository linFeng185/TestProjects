package com.lin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

/**
 * @Author: lin
 * @Date: 2021/1/6 15:17
 */
@SpringBootApplication
public class NacosApiApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(NacosApiApplication.class, args);
        System.out.println(LocalDateTime.now()+"--启动成功--");
    }
}
