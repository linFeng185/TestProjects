package com.lin.rabbit.project;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: linFeng
 * @Date: 2020/9/28 14:27
 * @Copyright: www.zektech.cn
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class MainTest {

    @Test
    void helloWord() {
        System.out.println("helloWord");
    }
}