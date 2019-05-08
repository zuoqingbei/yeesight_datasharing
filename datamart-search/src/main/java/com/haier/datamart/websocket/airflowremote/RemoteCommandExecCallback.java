package com.haier.datamart.websocket.airflowremote;

/**
 * Created by long on 2018/12/4.
 */
public interface RemoteCommandExecCallback {
    /**
     * 获取数据处理的方法
     * @param msg
     */
    public void doMsg(String msg);

    /**
     * 流程关闭的时候执行的方法
     */
    public void doClose();
}
