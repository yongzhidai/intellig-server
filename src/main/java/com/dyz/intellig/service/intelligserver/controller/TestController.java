package com.dyz.intellig.service.intelligserver.controller;

import com.alibaba.fastjson.JSON;
import com.dyz.intellig.service.intelligserver.dao.test.DispatchCarrierPO;
import com.dyz.intellig.service.intelligserver.service.SimulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by daiyongzhi on 2019/8/17.
 */
@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Resource
    private SimulationService simulationService;
    @Value("${env}")
    private String env;

    @RequestMapping("/test.do")
    public String test(String name){
        LOGGER.info(name +"---"+env);
        List<DispatchCarrierPO> list = simulationService.getAll();
        return JSON.toJSONString(list);
    }

    @RequestMapping("/info.do")
    public String info(String name){
        LOGGER.info(name+"---"+"这是info信息");
        return name;
    }

    @RequestMapping("/warn.do")
    public String warn(String name){
        LOGGER.warn(name+"---"+"这是warn信息");
        return name;
    }

    @RequestMapping("/error.do")
    public String error(String name){
        LOGGER.error(name+"---"+"这是error信息");
        return name;
    }
}
