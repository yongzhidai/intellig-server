package com.dyz.intellig.service.intelligserver.tcp.codec;

import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ClientRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by daiyongzhi on 2019/10/13.
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final byte[] array;
        final int offset;
        final int length = msg.readableBytes();
        if(length < 4){
            LOGGER.warn("不合法的消息码");
            return;
        }
        if (msg.hasArray()) {
            array = msg.array();
            offset = msg.arrayOffset() + msg.readerIndex();
        } else {
            array = ByteBufUtil.getBytes(msg, msg.readerIndex(), length, false);
            offset = 0;
        }

        out.add(new ClientRequest(array,offset,length));
    }
}
