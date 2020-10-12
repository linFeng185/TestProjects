package com.lin.rabbit.rabbit.config;

import com.lin.rabbit.enums.RabbitEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lin
 * @Date: 2020/9/29 18:29
 *
 */
@Configuration
public class Publish1Config {

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
}
