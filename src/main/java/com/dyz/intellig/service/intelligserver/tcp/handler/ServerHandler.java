package com.dyz.intellig.service.intelligserver.tcp.handler;

import com.dyz.intellig.service.intelligserver.tcp.msg.dispatcher.MsgDispatcher;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ClientRequest;
import com.dyz.intellig.service.intelligserver.tcp.session.DeviceSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        new DeviceSession(ctx.channel());
        LOGGER.info("有客户端连接："+ ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ClientRequest request = (ClientRequest) msg;

        DeviceSession deviceSession = DeviceSession.getInstance(ctx.channel());
        if(deviceSession == null){
            return;
        }

        MsgDispatcher.dispatchMsg(deviceSession, request);
        LOGGER.info("有客户端发来消息");

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 心跳处理
            DeviceSession deviceSession = DeviceSession.getInstance(ctx.channel());
            if(deviceSession != null){
                deviceSession.close();
            }
            LOGGER.info("心跳：有客户端断开连接---"+ ctx.channel().remoteAddress().toString());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        DeviceSession deviceSession = DeviceSession.getInstance(ctx.channel());
        if(deviceSession != null){
            deviceSession.close();
        }
        LOGGER.info("Inactive：有客户端断开连接---");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("Netty occurred error:{}",cause);
        DeviceSession deviceSession = DeviceSession.getInstance(ctx.channel());
        if(deviceSession != null){
            deviceSession.close();
        }
    }
}