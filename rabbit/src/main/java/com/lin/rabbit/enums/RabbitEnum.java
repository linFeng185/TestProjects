package com.lin.rabbit.enums;

/**
 * rabbit的实现类枚举
 * @Author:  lin
 * @Date: 2020/9/28 15:52
 *
 */
public enum  RabbitEnum {
    /**
     *  测试队列
     */
    RABBIT(0,"测试队列","rabbit.direct.routingKey.push","rabbit.direct.queue.push","rabbit.direct.exchange.push","",""),
    /**
     * work模式
     */
    WORK(1,"work模式","rabbit.direct.routingKey.work","rabbit.direct.queue.work","rabbit.direct.exchange.work","",""),
    /**
     * 发布，订阅模式，需要两个，因为需要两个队列，一个相同的交换机
     */
    PUBLISH(3,"发布，订阅模式",null,"rabbit.fanout.queue.publish1",null,"rabbit.fanout.exchange.publish",null),
    /**
     * 发布，订阅模式
     */
    PUBLISH2(3,"发布，订阅模式",null,"rabbit.fanout.queue.publish2",null,"rabbit.fanout.exchange.publish",null),
    /**
     * 路由模式
     */
    ROUTING(4,"路由模式","rabbit.direct.routingKey.routing","rabbit.direct.queue.routing","rabbit.direct.exchange.routing",null,null),
    /**
     * 主题模式
     */
    THEME(5,"主题模式","rabbit.topic.routingKey.theme","rabbit.topic.queue.theme",null,null,"rabbit.topic.exchange.theme"),
    ;
    /**
     * 标识
     */
    private int id;

    /**
     * 名称，作用
     */
    private String name;

    /**
     * 路由键
     */
    private String routingKey;

    /**
     * 队列
     */
    private String queue;

    /**
     * Direct交换机名
     */
    private String directExchange;

    /**
     * fanout交换机名
     */
    private String fanoutExchange;

    /**
     * topic交换机名
     */
    private String topicExchange;

    RabbitEnum(int id,String name,String routingKey,String queue,String directExchange,String fanoutExchange,String topicExchange){
        this.id=id;
        this.name=name;
        this.routingKey=routingKey;
        this.queue=queue;
        this.directExchange=directExchange;
        this.fanoutExchange=fanoutExchange;
        this.topicExchange=topicExchange;
    }

    public int getId() {
        return id;
    }

    public String getQueue() {
        return queue;
    }

    public String getDirectExchange() {
        return directExchange;
    }

    public String getFanoutExchange() {
        return fanoutExchange;
    }

    public String getTopicExchange() {
        return topicExchange;
    }

    public String getName() {
        return name;
    }

    public String getRoutingKey() {
        return routingKey;
    }}
