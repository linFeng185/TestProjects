package com.lin.context;

import com.lin.entity.ReqEntity;
import com.lin.entity.ResEntity;
import com.lin.strategy.Strategy1;
import com.lin.strategy.Strategy2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 策略分发的的根节点，需要实现StrategyRouter抽象类
 * @author lin
 * @date 2021/5/20 16:26
 **/
@Service
public class StrategyRootNode extends StrategyRouter<ReqEntity, ResEntity>{

    @Autowired
    private Strategy1 strategy1;
    @Autowired
    private Strategy2 strategy2;


    @Override
    public StrategyMapper<ReqEntity, ResEntity> registerStrategyMapper() {
        return param -> {
            int type=param.getType();
            if(type==1){
                return strategy1.getStrategyMapper().get(param);
            }
            if(type>1){
                return strategy2;
            }
            return null;
        };
    }
}
