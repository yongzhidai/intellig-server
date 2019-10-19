package com.dyz.intellig.service.intelligserver.sockettest;

import com.dyz.intellig.service.intelligserver.tcp.codec.MessageDecoder;
import com.dyz.intellig.service.intelligserver.tcp.codec.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by daiyongzhi on 2019/10/18.
 */
public class TcpClient {

    private static EventLoopGroup loopGroup = new NioEventLoopGroup();
    private static Bootstrap bootstrap = new Bootstrap();

    static{
        bootstrap.group(loopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
                ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                ch.pipeline().addLast("msDecoder", new MessageDecoder());
                ch.pipeline().addLast("msgEncoder", new MessageEncoder());
                ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(0, 10, 0));
            }
        });
    }

    public static TcpConnection getConnection(String ip,int port) throws InterruptedException {
        Channel channel = bootstrap.connect(ip,port).sync().channel();
        TcpConnection connection = new TcpConnection(channel);
        channel.pipeline().addLast(connection);
        return connection;
    }
}
