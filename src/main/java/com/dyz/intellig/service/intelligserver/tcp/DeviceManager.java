package com.dyz.intellig.service.intelligserver.tcp;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by daiyongzhi on 2019/8/25.
 *
 */
@Component
public class DeviceManager {
    private Map<Long,ChannelHandlerContext> deviceIdChannelMap = new ConcurrentHashMap<>();
    private Map<ChannelHandlerContext,Long> channelDeviceIdMap = new ConcurrentHashMap();

    public void addDevice(Long deviceId,ChannelHandlerContext ctx){
        deviceIdChannelMap.put(deviceId,ctx);
        channelDeviceIdMap.put(ctx,deviceId);
    }

    public void removeDevice(Long deviceId){
        ChannelHandlerContext ctx = deviceIdChannelMap.remove(deviceId);
        channelDeviceIdMap.remove(ctx);
    }

    public void removeDevice(ChannelHandlerContext ctx){
        Long deviceId = channelDeviceIdMap.remove(ctx);
        deviceIdChannelMap.remove(deviceId);
    }

}
