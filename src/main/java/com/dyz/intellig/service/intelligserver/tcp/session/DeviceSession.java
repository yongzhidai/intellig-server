package com.dyz.intellig.service.intelligserver.tcp.session;


import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ServerResponse;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * Created by daiyongzhi on 2019/10/14.
 */
public class DeviceSession {
    private Channel channel;

    private static final AttributeKey<DeviceSession> KEY_DEVICE_SESSION = AttributeKey.newInstance("device.session");

    public DeviceSession(Channel channel){
        this.channel = channel;
        Attribute<DeviceSession> attribute = this.channel.attr(KEY_DEVICE_SESSION);
        attribute.set(this);
    }

    public static DeviceSession getInstance(Channel channel) {
        Attribute<DeviceSession> attribute = channel.attr(KEY_DEVICE_SESSION);
        return attribute.get();
    }

    public void close() {
        this.channel.close();
    }

    public boolean isLogin() {
        return true;
    }

    public void sendMsg(ServerResponse response){
        if(response == null || channel == null || !channel.isActive()){
            return;
        }
        channel.writeAndFlush(response);
    }
}
