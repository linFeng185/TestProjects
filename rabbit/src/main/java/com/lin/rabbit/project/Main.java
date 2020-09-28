package com.lin.rabbit.project;

import com.lin.rabbit.project.service.IMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: linFeng
 * @Date: 2020/9/28 14:19
 * @Copyright: www.zektech.cn
 */
@RestController
public class Main {

    @Autowired
    private IMainService iMainService;

    @PostMapping("/helloWord")
    public String helloWord(){
        return iMainService.helloWord();
    }
}
