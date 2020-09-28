package com.lin.rabbit.project.service.impl;

import com.lin.rabbit.project.service.IMainService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: linFeng
 * @Date: 2020/9/28 14:42
 *
 */
@Service
public class MainServiceImpl implements IMainService {

    @Value("${dev.main}")
    private String hello;

    @Override
    public String helloWord() {
        return hello;
    }
}
