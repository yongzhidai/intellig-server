package com.dyz.intellig.service.intelligserver.tcp.msg.dispatcher;

import com.dyz.intellig.service.intelligserver.common.annotation.RequestProcessor;
import com.dyz.intellig.service.intelligserver.tcp.msg.transfer.ClientRequest;
import com.dyz.intellig.service.intelligserver.tcp.processor.AbstractRequestProcessor;
import com.dyz.intellig.service.intelligserver.tcp.processor.INotNeedLoginProcessor;
import com.dyz.intellig.service.intelligserver.tcp.session.DeviceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daiyongzhi on 2019/10/14.
 */
@Component
public class MsgDispatcher implements BeanPostProcessor{
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgDispatcher.class);

    private static Map<Integer,AbstractRequestProcessor> requestProcessorMap = new HashMap<>();


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        RequestProcessor requestProcessor = bean.getClass().getAnnotation(RequestProcessor.class);
        if(requestProcessor != null && bean instanceof AbstractRequestProcessor){
            requestProcessorMap.put(requestProcessor.msgCode(),(AbstractRequestProcessor) bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public static void dispatchMsg(DeviceSession deviceSession, ClientRequest request){
        int msgCode = request.getMsgCode();
        if(msgCode == 0){
            deviceSession.close();
        }
        if(msgCode%2 == 0){
            LOGGER.warn("消息码必须是奇数:{}",msgCode);
            return;
        }

        AbstractRequestProcessor processor = requestProcessorMap.get(msgCode);
        if(processor == null){
            LOGGER.warn("未识别的消息码:{}",msgCode);
            return;
        }
        if(deviceSession.isLogin() || processor instanceof INotNeedLoginProcessor){
            processor.handle(deviceSession,request);
        }else{
            LOGGER.warn("尚未登录，无法处理请求码:{}",msgCode);
            return;
        }
    }

}
