package com.dyz.intellig.service.intelligserver.tcp.processor;

import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ClientRequest;
import com.dyz.intellig.service.intelligserver.tcp.session.DeviceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.RequestProcessor;

/**
 * Created by daiyongzhi on 2019/10/14.
 */
public abstract class AbstractRequestProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RequestProcessor.class);

    public void handle(DeviceSession deviceSession, ClientRequest request){
        try {
            process(deviceSession,request);
        } catch (Exception e) {
            logger.error("消息处理出错，msg code:"+request.getMsgCode());
        }
    }

    protected abstract void process(DeviceSession deviceSession,ClientRequest request)throws Exception;
}
