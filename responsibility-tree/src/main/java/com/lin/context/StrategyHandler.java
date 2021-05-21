package com.lin.context;

/**
 * 策略处理者，所有节点（根节点除外）都必须实现这个接口
 * @param <P> 接口入参
 * @param <R> 接口返回值
 * @author lin
 * @date 2021/5/20 15:59
 **/
public interface StrategyHandler<P,R> {

    /**
     * 审批
     * @param param 参数
     * @return 返回值
     */
    R approve(P param);

}
