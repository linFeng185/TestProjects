package com.lin.strategy;

import com.lin.context.StrategyHandler;
import com.lin.entity.ReqEntity;
import com.lin.entity.ResEntity;
import org.springframework.stereotype.Service;

/**
 * 策略三
 * @author lin
 * @date 2021/5/20 16:52
 **/
@Service
public class Strategy3 implements StrategyHandler<ReqEntity, ResEntity> {
    @Override
    public ResEntity approve(ReqEntity param) {
        System.out.println("Strategy3");
        return new ResEntity();
    }
}
