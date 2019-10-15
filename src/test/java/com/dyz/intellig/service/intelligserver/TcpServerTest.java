package com.dyz.intellig.service.intelligserver;

import com.dyz.intellig.service.intelligserver.tcp.codec.MessageDecoder;
import com.dyz.intellig.service.intelligserver.tcp.codec.MessageEncoder;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ServerResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.util.StringUtils;

import java.util.Scanner;

/**
 * Created by daiyongzhi on 2019/8/26.
 */
public class TcpServerTest {


    public static void main(String[] args) throws Exception {
        int port = 11811;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65536, 0, 2, 0, 2));
                    ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                    ch.pipeline().addLast("msDecoder", new MessageDecoder());
                    ch.pipeline().addLast("msgEncoder", new MessageEncoder());
                    ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(0, 10, 0));
                    ch.pipeline().addLast(new TimeClientHandler());
                }
            });

            // Start the client.
            ChannelFuture f = b.connect("localhost", port).sync(); // (5)

            Scanner sc = new Scanner(System.in);
            String line = null;
            ServerResponse response = null;
            while (true) {
                line = sc.nextLine();
                if (StringUtils.isEmpty(line)) {
                    System.out.println("不能发送空信息!");
                    continue;
                }
                if ("send".equalsIgnoreCase(line)) {
                    if (response == null) {
                        System.out.println("请先输入要发送的数据");
                        continue;
                    }
                    f.channel().writeAndFlush(response);
                }else if("close".equalsIgnoreCase(line)){
                    f.channel().close().sync();
                    System.out.println("断开连接");
                }
                String[] strs = line.split(" ");
                if (strs.length != 2) {
                    continue;
                }
                if ("code".equalsIgnoreCase(strs[0])) {
                    response = new ServerResponse(Integer.parseInt(strs[1]));
                } else if (isNumeric(strs[0])) {
                    if (response == null) {
                        System.out.println("请先输入消息码");
                        continue;
                    }
                    int size = Integer.parseInt(strs[0]);
                    if (size == 1) {
                        response.writeByte(Byte.parseByte(strs[1]));
                    } else if (size == 2) {
                        response.writeShort(Short.parseShort(strs[1]));
                    } else if (size == 4) {
                        response.writeInt(Integer.parseInt(strs[1]));
                    } else if (size == 8) {
                        response.writeLong(Long.parseLong(strs[1]));
                    }
                } else if ("s".equalsIgnoreCase(strs[0])) {
                    if (response == null) {
                        System.out.println("请先输入消息码");
                        continue;
                    }
                    response.writeUTF(strs[1]);
                }
            }


        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }

}
