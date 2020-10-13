package com.lin.rabbit.rabbit.config;

import com.lin.rabbit.enums.RabbitEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 简单模式
 * @Author:  lin
 * @Date: 2020/9/28 15:45
 *
 */
@Configuration
public class RabbitConfig {

    /**
     * 将队列绑定到交换机中，并绑定消费者
     * @return
     */
    @Bean
    public Binding bindingExchange() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitEnum.RABBIT.getRoutingKey());
    }

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue queue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(RabbitEnum.RABBIT.getQueue(),true);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        return new DirectExchange(RabbitEnum.RABBIT.getDirectExchange(), true, false);
    }
}
