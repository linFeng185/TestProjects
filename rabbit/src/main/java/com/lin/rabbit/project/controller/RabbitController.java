package com.lin.rabbit.project.controller;

import com.lin.rabbit.project.service.IMainService;
import com.lin.rabbit.rabbit.service.PublishService;
import com.lin.rabbit.rabbit.service.PushService;
import com.lin.rabbit.rabbit.service.RabbitWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: lin
 * @Date: 2020/9/29 17:41
 *
 */
@RestController
public class RabbitController {

    @Autowired
    private IMainService iMainService;
    @Autowired
    private PushService pushService;
    @Autowired
    private RabbitWorkService rabbitWorkService;
    @Autowired
    private PublishService publishService;

    @PostMapping("/helloWord")
    public String helloWord(){
        return iMainService.helloWord();
    }

    /**
     * 测试队列
     * @return
     */
    @PostMapping("/push")
    public String push(){
        pushService.push();
        return "SUCCESS";
    }

    /**
     * work模式
     * @return
     * @throws InterruptedException
     */
    @PostMapping("/work")
    public String work() throws InterruptedException {
        rabbitWorkService.work();
        return "SUCCESS";
    }

    /**
     * 发布，订阅模式
     * @return
     */
    @PostMapping("/publish")
    public String publish(){
        publishService.publish();
        return "SUCCESS";
    }
}
