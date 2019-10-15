package com.dyz.intellig.service.intelligserver.tcp.processor.impl;

import com.dyz.intellig.service.intelligserver.common.annotation.RequestProcessor;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ClientRequest;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ServerResponse;
import com.dyz.intellig.service.intelligserver.tcp.processor.AbstractRequestProcessor;
import com.dyz.intellig.service.intelligserver.tcp.processor.INotNeedLoginProcessor;
import com.dyz.intellig.service.intelligserver.tcp.session.DeviceSession;

/**
 * Created by daiyongzhi on 2019/10/14.
 */
@RequestProcessor(msgCode = 1001)
public class LoginRequestProcessor1001 extends AbstractRequestProcessor implements INotNeedLoginProcessor {
    @Override
    protected void process(DeviceSession deviceSession, ClientRequest request) throws Exception {
        String name = request.readUTF();
        System.out.println(name+" 登录系统");
        ServerResponse response = new ServerResponse(1002);
        response.writeUTF("欢迎你");
        deviceSession.sendMsg(response);
    }
}
