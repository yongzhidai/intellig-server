package com.dyz.intellig.service.intelligserver.service;

import com.dyz.intellig.service.intelligserver.dao.test.DispatchCarrierPO;
import com.dyz.intellig.service.intelligserver.dao.test.DispatchCarrierPOMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by daiyongzhi on 2019/8/17.
 */
@Service
public class SimulationService {
    @Resource
    private DispatchCarrierPOMapper dispatchCarrierPOMapper;

    public List<DispatchCarrierPO> getAll(){

        return dispatchCarrierPOMapper.selectByExample(null);
    }
}
