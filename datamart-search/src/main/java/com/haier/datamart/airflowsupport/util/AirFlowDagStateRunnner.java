package com.haier.datamart.airflowsupport.util;

import com.haier.datamart.airflowsupport.AirFlowHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 新建的时候需要轮询dag的建立状态
 */
public class AirFlowDagStateRunnner implements Runnable {
    private Logger logger = LoggerFactory.getLogger(AirFlowDagStateRunnner.class);

    AirFlowDagStateOKCall call;
    private String dagName;
    public  AirFlowDagStateRunnner(){

    }
    public  AirFlowDagStateRunnner(String dagName,AirFlowDagStateOKCall call){
        this.call = call;
        this.dagName = dagName;
    }
    @Override
    public void run() {
        logger.info("开始读取状态："+this.dagName);
        if(waitForOK()){
           if(call!=null){
               call.call();
           }
        }
    }
    private boolean waitForOK(){
        int times = 20;//调用20次之后还不行就记录日志
        for(int i =0;i<times;i++){
            try{
                Thread.sleep(30*1000);//暂停30秒
                boolean result = AirFlowHandler.isRemoteReady(this.dagName);
                if(result){
                    logger.info("读取状态成功："+this.dagName);
                    return result;
                }
            }catch (Exception e){

            }
        }
        logger.info("读取状态失败："+this.dagName);
        return false;
    }
}
