package com.lin.rabbit.rabbit;

import org.springframework.amqp.core.*;

/**
 * @Author: linFeng
 * @Date: 2020/9/28 15:34
 * @Copyright: www.zektech.cn
 */
public interface IRabbitService {


    /**
     * 绑定交换机
     * @return
     */
    Binding bindingExchange();

    /**
     * 队列
     * @return
     */
    Queue queue();

    /**
     * Direct交换机
     * 如果路由键匹配，消息就会被投递到对应的队列
     * @return
     */
    DirectExchange directExchange();

    /**
     * fanout交换机
     * 当发送一套消息到fanout交换器时，它会把消息投递给所有附加在此交换器上的队列。
     * @return
     */
    FanoutExchange fanoutExchange();

    /**
     * topic交换机
     * 这类交换器可以使来自不同源头的消息能够达到同一队列。
     * @return
     */
    TopicExchange topicExchange();
}
