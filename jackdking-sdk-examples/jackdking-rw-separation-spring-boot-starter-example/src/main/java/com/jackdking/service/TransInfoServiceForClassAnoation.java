package com.jackdking.service;

import com.jackdking.dao.TransInfoMapper;
import com.jackdking.model.TransInfo;
import com.jackdking.rw.separation.annotation.RWSeparationDBContext;
import com.jackdking.rw.separation.enums.RWSeparationStrategyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RWSeparationDBContext(dsKey = "ds3", rwStrategyType = RWSeparationStrategyTypeEnum.RW_SEPARATION_WRITE_MASTER_READ_SLAVE)
@Service
public class TransInfoServiceForClassAnoation {

    @Autowired
    private TransInfoMapper transInfoMapper;

    public TransInfo queryDs0Record(Long id) {
        return transInfoMapper.get(id);
    }

    public TransInfo queryDs1Record(Long id) {
        return transInfoMapper.get(id);
    }

    public TransInfo queryMapperDs0Record(Long id) {
      return transInfoMapper.queryDsV1(id);
    }

    public TransInfo queryMapperDs1Record(Long id) {
      return transInfoMapper.queryDsV2(id, id);
    }

    public TransInfo queryMapperDs2Record(TransInfo transInfo, Long id) {
        TransInfo param = new TransInfo();
        param.setOrderid(id);
        return transInfoMapper.queryDsV3(param, id);
    }

    public TransInfo queryMapperDs3Record(TransInfo transInfo, Long id) {
        return transInfoMapper.queryDsV2(id, id);
    }

    public TransInfo queryMapperDs4Record(TransInfo transInfo, Long id) {
        return transInfoMapper.queryDsV2(id, id);
    }

}
