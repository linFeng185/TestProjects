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
 * work模式
 * @Author: lin
 * @Date: 2020/9/29 17:31
 *
 */
@Service
public class RabbitWorkService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布消息
     */
    public void work() throws InterruptedException {
        for (int i=0;i<20;i++){
            rabbitTemplate.convertAndSend(RabbitEnum.WORK.getDirectExchange(), RabbitEnum.WORK.getRoutingKey(),"helloWord");
            //每发布一条消息，休眠一段时间
            Thread.sleep(i * 20);
        }
    }

    /**
     * 推送队列的监听1
     * @param
     * @param channel
     * @param tag
     * @throws IOException
     */
    @RabbitListener(queues = "rabbit.direct.queue.work")
    public void consume(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, InterruptedException {
        System.out.println("1：收到消息"+str);
        channel.basicAck(tag,true);
        Thread.sleep(100);
    }

    /**
     * 推送队列的监听1
     * @param
     * @param channel
     * @param tag
     * @throws IOException
     */
    @RabbitListener(queues = "rabbit.direct.queue.work")
    public void consume2(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, InterruptedException {
        System.out.println("2：收到消息"+str);
        channel.basicAck(tag,true);
        Thread.sleep(200);
    }
}
