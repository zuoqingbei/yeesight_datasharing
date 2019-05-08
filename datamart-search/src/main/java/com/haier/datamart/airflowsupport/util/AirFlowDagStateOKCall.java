package com.haier.datamart.airflowsupport.util;

/**
 * 轮询成功之后要调用的操作
 */
public interface AirFlowDagStateOKCall {
    public void call();
}
