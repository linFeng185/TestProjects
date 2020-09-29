package com.lin.rabbit.rabbit.config.impl;

import com.lin.rabbit.enums.RabbitEnum;
import com.lin.rabbit.rabbit.config.IRabbitConfig;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: lin
 * @Date: 2020/9/29 18:29
 *
 */
@Configuration
public class Publish2ConfigImpl implements IRabbitConfig {
    @Override
    @Bean
    public Binding bindingExchange() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitEnum.PUBLISH2.getRoutingKey());
    }

    @Override
    @Bean
    public Queue queue() {
        return new Queue(RabbitEnum.PUBLISH2.getQueue(),true);
    }

    @Override
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitEnum.PUBLISH2.getDirectExchange(),true,false);
    }

    @Override
    public FanoutExchange fanoutExchange() {
        return null;
    }

    @Override
    public TopicExchange topicExchange() {
        return null;
    }
}
