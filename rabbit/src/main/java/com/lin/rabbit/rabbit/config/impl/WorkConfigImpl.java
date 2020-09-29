package com.lin.rabbit.rabbit.config.impl;

import com.lin.rabbit.enums.RabbitEnum;
import com.lin.rabbit.rabbit.config.IRabbitConfig;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: linFeng
 * @Date: 2020/9/29 17:45
 * @Copyright: www.zektech.cn
 */
@Configuration
public class WorkConfigImpl implements IRabbitConfig {
    @Override
    @Bean
    public Binding bindingExchange() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitEnum.WORK.getRoutingKey());
    }

    @Override
    @Bean
    public Queue queue() {
        return new Queue(RabbitEnum.WORK.getQueue(),true);
    }

    @Override
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitEnum.WORK.getDirectExchange(),true,false);
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
