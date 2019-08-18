package com.dyz.intellig.service.intelligserver.dao.test;

import com.dyz.intellig.service.intelligserver.dao.test.DispatchCarrierPO;
import com.dyz.intellig.service.intelligserver.dao.test.DispatchCarrierPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DispatchCarrierPOMapper {
    int countByExample(DispatchCarrierPOExample example);

    int deleteByExample(DispatchCarrierPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DispatchCarrierPO record);

    int insertSelective(DispatchCarrierPO record);

    List<DispatchCarrierPO> selectByExample(DispatchCarrierPOExample example);

    DispatchCarrierPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DispatchCarrierPO record, @Param("example") DispatchCarrierPOExample example);

    int updateByExample(@Param("record") DispatchCarrierPO record, @Param("example") DispatchCarrierPOExample example);

    int updateByPrimaryKeySelective(DispatchCarrierPO record);

    int updateByPrimaryKey(DispatchCarrierPO record);
}