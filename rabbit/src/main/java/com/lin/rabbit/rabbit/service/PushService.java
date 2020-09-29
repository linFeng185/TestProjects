package com.lin.rabbit.rabbit.service;

import com.lin.rabbit.enums.RabbitEnum;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @Author:  lin
 * @Date: 2020/9/29 10:19
 *
 */
@Service
public class PushService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 推送方法
     */
    public void push(){
        System.out.println("开始推送");
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend(RabbitEnum.RABBIT.getDirectExchange(), RabbitEnum.RABBIT.getRoutingKey(),"helloWord", new CorrelationData("1"));
//        return "ok";
        System.out.println("结束推送");
    }

    /**
     * 推送队列的监听
     * 注解中为监听的队列
     * @param
     * @param channel
     * @param tag
     * @throws IOException
     */
    @RabbitListener(queues = "rabbit.direct.queue.push")
    public void processDirect(String str, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("收到消息");
        System.out.println(str);
        channel.basicAck(tag,true);
    }

    /*@PostConstruct
    public void setup() {
        // 消息发送完毕后，则回调此方法 ack代表发送是否成功
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            // ack为true，代表MQ已经准确收到消息
            if (!ack) {
                System.out.println("发送消息到MQ失败，原因："+cause);
                return;
            }
            System.out.println(correlationData.getId()+":成功发送消息到MQ");

        });
    }*/
}
