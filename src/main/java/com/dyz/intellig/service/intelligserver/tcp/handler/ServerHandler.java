package com.dyz.intellig.service.intelligserver.tcp.handler;

import com.dyz.intellig.service.intelligserver.tcp.DeviceManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LOGGER.info("有客户端连接："+ ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        try {
            byte[] data = "hello\n".getBytes();
            ByteBuf buf = ctx.alloc().buffer(data.length+2);
            byte[] tmp = new byte[data.length+2];
            tmp[0] = 0;
            tmp[1] = 5;
            for(int i=0;i<data.length;i++){
                tmp[2+i] = data[i];
            }
            buf.writeBytes(tmp);
            ctx.writeAndFlush(buf);
        } finally {
            ReferenceCountUtil.release(m);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("Netty occurred error:{}",cause);
        ctx.close();
        DeviceManager.removeDevice(ctx);
    }
}