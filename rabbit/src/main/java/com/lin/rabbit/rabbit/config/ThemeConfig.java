package com.lin.rabbit.rabbit.config;

import com.lin.rabbit.enums.RabbitEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题模式创建队列和交换机
 * 创建一个交换机，三个队列，队列的路由都不相同，发送消息时用路由键和通配符模糊匹配相应的队列，用.分隔
 * #：可以匹配任意数量的分隔位数量
 * *：只能匹配一个分隔位的数量
 * 例如 a.*只能匹配到a.XXX，而不能匹配到a.XXX.XXX 而a.#可以匹配到a.XXX.XXX
 * @Author: lin
 * @Date: 2020/10/14 15:45
 *
 */
@Configuration
public class ThemeConfig {

    @Bean
    public Binding bindingThemeExchange(){
        return BindingBuilder.bind(themeQueue()).to(themeExchange()).with(RabbitEnum.THEME.getRoutingKey());
    }

    @Bean
    public TopicExchange themeExchange(){
        return new TopicExchange(RabbitEnum.THEME.getTopicExchange(), true, false);
    }

    @Bean
    public Queue themeQueue(){
        return new Queue(RabbitEnum.THEME.getQueue(),true);
    }

    @Bean
    public Binding bindingThemeExchange2(){
        return BindingBuilder.bind(themeQueue2()).to(themeExchange2()).with("rabbit.topic.routingKey.theme.#");
    }

    @Bean
    public TopicExchange themeExchange2(){
        return new TopicExchange(RabbitEnum.THEME.getTopicExchange(), true, false);
    }

    @Bean
    public Queue themeQueue2(){
        return new Queue("rabbit.topic.queue.theme2",true);
    }

    @Bean
    public Binding bindingThemeExchange3(){
        return BindingBuilder.bind(themeQueue3()).to(themeExchange3()).with("rabbit.topic.routingKey.theme.*");
    }

    @Bean
    public TopicExchange themeExchange3(){
        return new TopicExchange(RabbitEnum.THEME.getTopicExchange(), true, false);
    }

    @Bean
    public Queue themeQueue3(){
        return new Queue("rabbit.topic.queue.theme3",true);
    }


}
