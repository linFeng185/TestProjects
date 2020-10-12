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
public class Publish2Config {

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
