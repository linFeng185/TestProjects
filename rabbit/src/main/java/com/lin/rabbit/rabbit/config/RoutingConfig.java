package com.lin.rabbit.rabbit.config;

import com.lin.rabbit.enums.RabbitEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * routing路由模式
 * 创建多个队列，多个队列可以绑定同样的路由键，生产者发布消息后，交换机根据路由键匹配相应的队列
 * @Author: lin
 * @Date: 2020/10/13 15:06
 */
@Configuration
public class RoutingConfig {

    @Bean
    public Binding bindingRoutingExchange(){
        return BindingBuilder.bind(routingQueue()).to(routingExchange()).with(RabbitEnum.ROUTING.getRoutingKey());
    }

    @Bean
    public DirectExchange routingExchange(){
        return new DirectExchange(RabbitEnum.ROUTING.getDirectExchange(), true, false);
    }

    @Bean
    public Queue routingQueue(){
        return new Queue(RabbitEnum.ROUTING.getQueue(),true);
    }

    @Bean
    public Binding bindingRoutingExchange2(){
        return BindingBuilder.bind(routingQueue2()).to(routingExchange2()).with(RabbitEnum.ROUTING.getRoutingKey());
    }

    @Bean
    public DirectExchange routingExchange2(){
        return new DirectExchange(RabbitEnum.ROUTING.getDirectExchange(), true, false);
    }

    @Bean
    public Queue routingQueue2(){
        return new Queue("rabbit.direct.queue.routing2",true);
    }

    @Bean
    public Binding bindingRoutingExchange3(){
        return BindingBuilder.bind(routingQueue3()).to(routingExchange3()).with("rabbit.direct.routingKey.routing3");
    }

    @Bean
    public DirectExchange routingExchange3(){
        return new DirectExchange(RabbitEnum.ROUTING.getDirectExchange(), true, false);
    }

    @Bean
    public Queue routingQueue3(){
        return new Queue("rabbit.direct.queue.routing3",true);
    }
}
