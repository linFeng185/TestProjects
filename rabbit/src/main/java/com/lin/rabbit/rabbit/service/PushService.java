package com.lin.rabbit.rabbit.service;

import com.lin.rabbit.enums.RabbitEnum;
import com.lin.rabbit.util.core.SpringContextHolder;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: linFeng
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
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>(3);
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
//        return "ok";
        System.out.println("结束推送");
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

    @PostConstruct
    public void setup() {
        // 消息发送完毕后，则回调此方法 ack代表发送是否成功
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("进入回调。。。");
            // ack为true，代表MQ已经准确收到消息
            if (!ack) {
                System.out.println("发送消息到MQ失败");
                System.out.println("ConfirmCallback: "+"原因："+cause);
                return;
            }
            try {
                System.out.println(correlationData.getId()+":成功发送消息到MQ");
                // 修改本地消息表的状态为“已发送”。
                //TODO:修改本地表
            } catch (Exception e) {
                System.out.println("警告：修改本地消息表的状态时出现异常");
            }

        });
    }

    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>(3);
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

}
