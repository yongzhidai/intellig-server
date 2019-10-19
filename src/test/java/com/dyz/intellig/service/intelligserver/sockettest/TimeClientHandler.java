package com.dyz.intellig.service.intelligserver.sockettest;

import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.InMsg;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.OutMsg;
import com.dyz.intellig.service.intelligserver.tcp.session.DeviceSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new DeviceSession(ctx.channel());


    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            InMsg request = (InMsg) msg;
            if(request.getMsgCode() == 2){
            }else if(request.getMsgCode() == 1002){
                System.out.println("收到登录响应:"+ request.readUTF());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Netty Client occurred error:{}");
        DeviceSession deviceSession = DeviceSession.getInstance(ctx.channel());
        if(deviceSession != null){
            deviceSession.close();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            DeviceSession deviceSession = DeviceSession.getInstance(ctx.channel());
            deviceSession.sendMsg(new OutMsg(1));
        }
    }
}