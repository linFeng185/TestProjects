package com.lin.rabbit.rabbit.config.impl;

import com.lin.rabbit.rabbit.config.IRabbitConfig;
import org.springframework.amqp.core.*;

/**
 * @Author: lin
 * @Date: 2020/9/29 18:29
 *
 */
public class Publish2ConfigImpl implements IRabbitConfig {
    @Override
    public Binding bindingExchange() {
        return null;
    }

    @Override
    public Queue queue() {
        return null;
    }

    @Override
    public DirectExchange directExchange() {
        return null;
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
