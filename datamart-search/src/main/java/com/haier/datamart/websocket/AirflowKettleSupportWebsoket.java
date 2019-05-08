package com.haier.datamart.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.haier.datamart.airflowsupport.AirFlowHandler;
import com.haier.datamart.websocket.airflowremote.RemoteCommandExecCallback;
import com.haier.datamart.websocket.airflowremote.RemoteCommandExecClient;

/**
 *
 * @author 刘志龙
 * @date 2018-11-13
 * websoket
 */
@Component
@ServerEndpoint(value = "/websocket/ariflow/commandtest")
public class AirflowKettleSupportWebsoket  {
	private static  Logger logger = LoggerFactory.getLogger(AirflowKettleSupportWebsoket.class);

	private boolean isRunning = false;
	private Session session;
	/**
	 * 连接建立成功调用的方法*/
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		try {
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息*/
	@OnMessage
	public void onMessage(String message, Session session) {
		if(isRunning){
			return;
		}
		isRunning = true;
		//默认message是命令
		//启动执行命令
		try {
			new RemoteCommandExecClient(AirFlowHandler.COMMAND_TEST_HOST,AirFlowHandler.COMMAND_TEST_PORT).start("netstat -ano", new RemoteCommandExecCallback() {
				@Override
				public void doMsg(String msg) {
					try {
						session.getBasicRemote().sendText(msg);
					} catch (IOException e) {
						e.printStackTrace();
						doClose();
					}
				}
				@Override
				public void doClose() {
					//发送一条停止的数据
					try {
						session.getBasicRemote().sendText("END_OF_THE_COMMAND_EXEC_a6f762bef");
						session.close();
					} catch (IOException e) {
					}
					System.out.println("IM close");
				}
			});
		}catch (Exception e){
			try{
				session.getBasicRemote().sendText("END_OF_THE_COMMAND_EXEC_a6f762bef");
				session.close();
			}catch (Exception es){

			}
		}


	}

	 @OnError
	 public void onError(Session session, Throwable error) {
		 error.printStackTrace();
		 try{
			 session.getBasicRemote().sendText("END_OF_THE_COMMAND_EXEC_a6f762bef");
			 session.close();
		 }catch (Exception es){

		 }
	 }

}

