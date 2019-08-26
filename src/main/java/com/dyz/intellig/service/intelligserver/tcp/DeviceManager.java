package com.dyz.intellig.service.intelligserver.tcp;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by daiyongzhi on 2019/8/25.
 *
 */
public class DeviceManager {
    private static Map<Long,ChannelHandlerContext> deviceIdChannelMap = new ConcurrentHashMap<>();
    private static Map<ChannelHandlerContext,Long> channelDeviceIdMap = new ConcurrentHashMap();

    public static void addDevice(Long deviceId,ChannelHandlerContext ctx){
        deviceIdChannelMap.put(deviceId,ctx);
        channelDeviceIdMap.put(ctx,deviceId);
    }

    public static void removeDevice(Long deviceId){
        ChannelHandlerContext ctx = deviceIdChannelMap.remove(deviceId);
        channelDeviceIdMap.remove(ctx);
    }

    public static void removeDevice(ChannelHandlerContext ctx){
        Long deviceId = channelDeviceIdMap.remove(ctx);
        deviceIdChannelMap.remove(deviceId);
    }

}
