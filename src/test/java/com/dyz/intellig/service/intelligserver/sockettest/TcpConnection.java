package com.dyz.intellig.service.intelligserver.sockettest;

import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.InMsg;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.OutMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by daiyongzhi on 2019/10/18.
 */
public class TcpConnection extends ChannelInboundHandlerAdapter {

    private Channel channel;

    private volatile InMsg clientRequest;
    private volatile Thread readThread;

    public TcpConnection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        InMsg request = (InMsg) msg;
        clientRequest = request;
        if(readThread != null){
            LockSupport.unpark(readThread);
        }
    }

    public InMsg getResponse(){
        if(clientRequest != null){
            return clientRequest;
        }
        readThread = Thread.currentThread();
        LockSupport.park();
        readThread = null;
        return clientRequest;
    }

    public void sendMsg(OutMsg response) throws InterruptedException {
        clientRequest = null;
        channel.writeAndFlush(response).sync();
    }

    public void close() throws InterruptedException {
        channel.close().sync();
    }
}
