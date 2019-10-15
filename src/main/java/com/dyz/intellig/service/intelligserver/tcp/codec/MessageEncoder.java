package com.dyz.intellig.service.intelligserver.tcp.codec;

import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ServerResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by daiyongzhi on 2019/10/13.
 */
public class MessageEncoder extends MessageToMessageEncoder<ServerResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ServerResponse msg, List<Object> out) throws Exception {
        if(msg == null){
            return;
        }

        byte[] datas = msg.toBytes();

        ByteBuf byteBuf = ctx.alloc().buffer(datas.length);

        byteBuf.writeBytes(datas);

        out.add(byteBuf);

    }
}
