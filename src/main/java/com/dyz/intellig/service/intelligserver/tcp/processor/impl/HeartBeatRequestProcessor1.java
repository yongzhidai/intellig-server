package com.dyz.intellig.service.intelligserver.tcp.processor.impl;

import com.dyz.intellig.service.intelligserver.common.annotation.RequestProcessor;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ClientRequest;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ServerResponse;
import com.dyz.intellig.service.intelligserver.tcp.processor.AbstractRequestProcessor;
import com.dyz.intellig.service.intelligserver.tcp.processor.INotNeedLoginProcessor;
import com.dyz.intellig.service.intelligserver.tcp.session.DeviceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by daiyongzhi on 2019/10/14.
 */
@RequestProcessor(msgCode = 1)
public class HeartBeatRequestProcessor1 extends AbstractRequestProcessor implements INotNeedLoginProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatRequestProcessor1.class);

    @Override
    public void process(DeviceSession deviceSession, ClientRequest request) throws Exception {
        LOGGER.info("rcv: 心跳");
        deviceSession.sendMsg(new ServerResponse(2));
    }
}
