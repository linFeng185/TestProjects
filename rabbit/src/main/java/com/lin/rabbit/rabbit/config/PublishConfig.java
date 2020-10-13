package com.lin.rabbit.rabbit.config;

import com.lin.rabbit.enums.RabbitEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布订阅模式
 * @Author: lin
 * @Date: 2020/9/29 18:29
 *
 */
@Configuration
public class PublishConfig {

    @Bean
    public Binding bindingPublishExchange1() {
        return BindingBuilder.bind(publishQueue1()).to(publishFanoutExchange1());
    }

    @Bean
    public Queue publishQueue1() {
        return new Queue(RabbitEnum.PUBLISH.getQueue(),true);
    }

    @Bean
    public FanoutExchange publishFanoutExchange1() {
        return new FanoutExchange(RabbitEnum.PUBLISH.getFanoutExchange(),true,false);
    }

    @Bean
    public Binding bindingPublishExchange2() {
        return BindingBuilder.bind(publishQueue2()).to(publishFanoutExchange2());
    }

    @Bean
    public Queue publishQueue2() {
        return new Queue(RabbitEnum.PUBLISH2.getQueue(),true);
    }

    @Bean
    public FanoutExchange publishFanoutExchange2() {
        return new FanoutExchange(RabbitEnum.PUBLISH2.getFanoutExchange(),true,false);
    }
}
