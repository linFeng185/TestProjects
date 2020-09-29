package com.lin.rabbit.rabbit.service;

import com.lin.rabbit.enums.RabbitEnum;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: lin
 * @Date: 2020/9/29 18:30
 *
 */
@Service
public class PublishService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布消息
     */
    public void publish() {
        System.out.println("发送消息");
        rabbitTemplate.convertAndSend(RabbitEnum.WORK.getDirectExchange(), RabbitEnum.WORK.getDirectExchange(),"helloWord");
    }

    /**
     * 队列的监听1
     * @param
     * @param channel
     * @param tag
     * @throws IOException
     */
    @RabbitListener(queues = "rabbit.direct.queue.publish1")
    public void consume1(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("1：收到消息"+str);
        channel.basicAck(tag,true);
    }

    /**
     * 队列的监听2
     * @param
     * @param channel
     * @param tag
     * @throws IOException
     */
    @RabbitListener(queues = "rabbit.direct.queue.publish2")
    public void consume2(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("2：收到消息"+str);
        channel.basicAck(tag,true);
    }
}
