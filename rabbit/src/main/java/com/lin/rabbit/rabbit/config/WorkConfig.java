package com.lin.rabbit.rabbit.config;

import com.lin.rabbit.enums.RabbitEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工作模式（资源竞争）
 * @Author: lin
 * @Date: 2020/9/29 17:45
 *
 */
@Configuration
public class WorkConfig {

    @Bean
    public Binding bindingWorkExchange() {
        return BindingBuilder.bind(workQueue()).to(workDirectExchange()).with(RabbitEnum.WORK.getRoutingKey());
    }

    @Bean
    public Queue workQueue() {
        return new Queue(RabbitEnum.WORK.getQueue(),true);
    }

    @Bean
    public DirectExchange workDirectExchange() {
        return new DirectExchange(RabbitEnum.WORK.getDirectExchange(),true,false);
    }
}
