package com.lin.rabbit.rabbit.config;

import com.lin.rabbit.enums.RabbitEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * routing路由模式
 * @Author: linFeng
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
