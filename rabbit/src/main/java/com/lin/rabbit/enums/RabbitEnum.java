package com.lin.rabbit.enums;

/**
 * rabbit的实现类枚举
 * @Author: linFeng
 * @Date: 2020/9/28 15:52
 *
 */
public enum  RabbitEnum {
    /**
     *  测试队列
     */
    RABBIT(0,"测试队列","rabbit.direct.routingKey.push","rabbit.direct.queue.push","rabbit.direct.exchange.push","",""),;

    /**
     * 唯一标识
     */
    private int id;

    /**
     * 名称，作用
     */
    private String name;

    /**
     * 消费者
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
