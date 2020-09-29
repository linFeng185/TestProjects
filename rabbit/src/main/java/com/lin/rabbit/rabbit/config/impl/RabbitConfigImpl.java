package com.lin.rabbit.rabbit.config.impl;

import com.lin.rabbit.annotation.RabbitAnnotation;
import com.lin.rabbit.enums.RabbitEnum;
import com.lin.rabbit.rabbit.config.IRabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * @Author: linFeng
 * @Date: 2020/9/28 15:45
 *
 */
@RabbitAnnotation(RabbitEnum.RABBIT)
@Configuration
public class RabbitConfigImpl implements IRabbitConfig {

    private RabbitEnum rabbitEnum=RabbitEnum.RABBIT;

    @Override
    public Binding bindingExchange() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(rabbitEnum.getRoutingKey());
    }

    @Override
    public Queue queue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(rabbitEnum.getQueue(),true);
    }

    @Override
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitEnum.getDirectExchange(),true,false);
    }

    @Override
    public FanoutExchange fanoutExchange() {
        return null;
    }

    @Override
    public TopicExchange topicExchange() {
        return null;
    }

    /**
     * 微信推送队列的监听
     * @param
     * @param channel
     * @param tag
     * @throws IOException
     */
    @RabbitListener(queues = "rabbit.direct.queue.push")
    public void processDirect(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println(str);
        channel.basicAck(tag,true);
    }
}
