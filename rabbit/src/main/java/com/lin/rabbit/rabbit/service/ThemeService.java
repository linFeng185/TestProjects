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
 * @Date: 2020/10/14 15:45
 *
 */
@Service
public class ThemeService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param routingKey 要发送到的路由键
     */
    public void theme(String routingKey){
        //#：代表一个单词
        //*：代表多个单词
        System.out.println("发送消息");
        rabbitTemplate.convertAndSend(RabbitEnum.THEME.getTopicExchange(),routingKey,"主题");
    }

    /**
     * 监听消息
     * @param str
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = "rabbit.topic.queue.theme")
    public void consume(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("theme收到消息："+str);
        channel.basicAck(tag,true);
    }

    /**
     * 监听消息
     * @param str
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = "rabbit.topic.queue.theme2")
    public void consume2(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("theme2收到消息："+str);
        channel.basicAck(tag,true);
    }
    /**
     * 监听消息
     * @param str
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = "rabbit.topic.queue.theme3")
    public void consume3(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("theme3收到消息："+str);
        channel.basicAck(tag,true);
    }

}
