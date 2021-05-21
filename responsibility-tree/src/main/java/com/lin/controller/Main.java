package com.lin.controller;

import com.lin.context.StrategyRootNode;
import com.lin.entity.ReqEntity;
import com.lin.entity.ResEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lin
 * @date 2021/5/20 17:43
 **/
@RestController
public class Main {

    @Autowired
    private StrategyRootNode strategyRootNode;

    @RequestMapping("/getStrategy")
    public ResEntity getStrategy(ReqEntity reqEntity){
        return strategyRootNode.strategyApprove(reqEntity);
    }
}
