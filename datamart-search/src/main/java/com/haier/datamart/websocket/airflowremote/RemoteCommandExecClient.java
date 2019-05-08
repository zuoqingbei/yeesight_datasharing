package com.haier.datamart.websocket.airflowremote;

import com.haier.datamart.service.impl.AirflowKettleSupportServiceImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * liuzhilong
 * 执行远程命令的客户端
 */
public class RemoteCommandExecClient {
    private static Logger logger = LoggerFactory.getLogger(RemoteCommandExecClient.class);

    private String host;
    private int port;
    private RemoteCommandExecCallback remoteCommandExecCallback;
    private String command;
    public RemoteCommandExecClient( String host,int port){
        this.host = host;
        this.port = port;
    }
    public  void start(String command,RemoteCommandExecCallback remoteCommandExecCallback) throws Exception {
        this.remoteCommandExecCallback = remoteCommandExecCallback;
        this.command = command;
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            logger.info("connected...");
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            logger.info("created..");

            ChannelFuture cf = b.connect().sync(); // 异步连接服务器
            logger.info("connected..."); // 连接完成

            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
            logger.info("closed.."); // 关闭完成
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
    }

    public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
        /**
         * 启动的时候 执行操作
         * 发送要执行的命令
         * @param ctx
         * @throws Exception
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                     logger.info("client channelActive..,exec "+command);
                     ctx.writeAndFlush(Unpooled.copiedBuffer(command, CharsetUtil.UTF_8)); // 必须有flush
                        super.channelActive(ctx);
         }


        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            logger.info("client channelRead..");
            String data = msg.toString(Charset.forName("utf-8"));
             logger.info("Client received:" + ByteBufUtil.hexDump(msg) + "; The value is:" + data);
            //如果是 协商好的退出命令  那么需要退出
            if("END_OF_THE_COMMAND_EXEC_a6f762bef".equals(data)){
                try {
                    remoteCommandExecCallback.doClose();
                }catch (Exception e){
                }
                ctx.channel().close().sync();// client关闭channel连接
            }else{
                try {
                    remoteCommandExecCallback.doMsg(data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
             cause.printStackTrace();
             logger.error("error",cause);
             ctx.close();
         }
        /*
       * 关闭 客户端 触发
       */
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            logger.info("Client close ");
            super.channelInactive(ctx);
        }
    }
}
