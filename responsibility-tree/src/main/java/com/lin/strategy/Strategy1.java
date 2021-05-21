package com.lin.strategy;

import com.lin.context.StrategyHandler;
import com.lin.context.StrategyRouter;
import com.lin.entity.ReqEntity;
import com.lin.entity.ResEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 策略一
 * @author lin
 * @date 2021/5/20 16:51
 **/
@Service
public class Strategy1 extends StrategyRouter<ReqEntity, ResEntity> implements StrategyHandler<ReqEntity, ResEntity> {

    @Autowired
    private Strategy3 strategy3;

    @Override
    public StrategyMapper<ReqEntity, ResEntity> registerStrategyMapper() {
        return param -> {
            Double childVersion=param.getChildVersion();
            if(childVersion == null){
                return this;
            }
            if(childVersion>0){
                return strategy3;
            }
            return null;
        };
    }

    @Override
    public ResEntity approve(ReqEntity param) {
        System.out.println("Strategy1");
        return new ResEntity();
    }
}
