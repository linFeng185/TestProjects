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
 * @Author: linFeng
 * @Date: 2020/10/14 15:17
 * @Copyright: www.zektech.cn
 */
@Service
public class RoutingService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void routing(){
        //将消息发送至绑定了该路由的队列中
        System.out.println("发送消息");
        rabbitTemplate.convertAndSend(RabbitEnum.ROUTING.getDirectExchange(),RabbitEnum.ROUTING.getRoutingKey(),"路由");
    }

    /**
     * 监听消息
     * @param str
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = "rabbit.direct.queue.routing")
    public void consume(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("routing收到消息："+str);
        channel.basicAck(tag,true);
    }

    /**
     * 监听消息
     * @param str
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = "rabbit.direct.queue.routing2")
    public void consume2(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("routing2收到消息："+str);
        channel.basicAck(tag,true);
    }

    /**
     * 监听消息
     * @param str
     * @param channel
     * @param tag
     */
    @RabbitListener(queues = "rabbit.direct.queue.routing3")
    public void consume3(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("routing3收到消息："+str);
        channel.basicAck(tag,true);
    }
}
