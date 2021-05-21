package com.lin.context;

import javax.annotation.PostConstruct;

/**
 * 策略路由，根据入参来决定执行哪个策略
 * 通过实现 {@code StrategyRouter} 抽象类来完成策略的分发，只有根节点和子节点需要实现这个类，叶节点无需实现这个类
 * 通过实现 {@code StrategyHandler} 接口来实现不同策略的具体执行逻辑
 * 根节点只负责根据入参进行策略分发，只需要实现StrategyRouter抽象类，而子节点既负责策略的分发，也需要实现不同的策略，所以子节点需要实现StrategyHandler接口
 * 叶节点不需要根据入参进行策略分发，所以只需要实现StrategyHandler就可以了
 * <pre>
 *           +---------+
 *           |  Root   |   ----------- 第 1 层策略入口
 *           +---------+
 *            /       \  ------------- 根据入参 P1 进行策略分发
 *           /         \
 *     +------+      +------+
 *     |  A   |      |  B   |  ------- 第 2 层不同策略的实现
 *     +------+      +------+
 *       /  \          /  \  --------- 根据入参 P2 进行策略分发
 *      /    \        /    \
 *   +---+  +---+  +---+  +---+
 *   |A1 |  |A2 |  |B1 |  |B2 |  ----- 第 3 层不同策略的实现
 *   +---+  +---+  +---+  +---+
 * </pre>
 * @param <P> 入参
 * @param <R> 返回值
 * @author lin
 * @date 2021/5/20 16:12
 **/
public abstract class StrategyRouter<P,R> {

    /**
     * 策略的映射器，根据入参来路由到指定的策略执行者
     * @param <P>
     * @param <R>
     */
    public interface StrategyMapper<P,R>{

        /**
         * 根据入参获取到对应的策略执行者
         * @param param 入参
         * @return 具体的执行者
         */
        StrategyHandler<P,R> get(P param);
    }

    /**
     * 在类初始化时，会调用初始化类（也就是实现类）的registerStrategyMapper()方法获取到接口的实现
     */
    protected StrategyMapper<P,R> strategyMapper;

    /**
     * 类初始化时注册分发策略 Mapper
     */
    @PostConstruct
    private void abstractInit() {
        //获取到StrategyMapper接口的实现
        strategyMapper = registerStrategyMapper();
        if(strategyMapper == null){
            throw new NullPointerException("未找到策略分发者");
        }
    }

    /**
     * 执行审批方法
     * @param param 入参
     * @return 返回值
     */
    public R strategyApprove(P param){
        //调用实现StrategyMapper接口的匿名类的get方法，此时get方法会根据入参，返回对应的策略执行者，再调用approve方法
        return strategyMapper.get(param).approve(param);
    }

    /**
     * 分发策略的具体实现
     * @return 策略映射器
     */
    public abstract StrategyMapper<P,R> registerStrategyMapper();
}
