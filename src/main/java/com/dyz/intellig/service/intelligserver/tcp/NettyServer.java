package com.dyz.intellig.service.intelligserver.tcp;

import com.dyz.intellig.service.intelligserver.tcp.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.stereotype.Component;

/**
 * Created by daiyongzhi on 2019/8/25.
 */
@Component
public class NettyServer {
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private DefaultEventExecutorGroup bizGroup = new DefaultEventExecutorGroup(100,new DefaultThreadFactory("SERVER_BIZ_Thread"));
    private ChannelFuture channelFuture;

    public void start(int port) throws Exception{
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(bizGroup,"frameDecoder",new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
                        ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                        ch.pipeline().addLast("serverHandler",new ServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        channelFuture = b.bind(port).sync();
    }

    public void destory()throws Exception{
        channelFuture.channel().close().sync();
        workerGroup.shutdownGracefully().sync();
        bossGroup.shutdownGracefully().sync();
    }
}
