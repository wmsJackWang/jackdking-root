package com.jackdking.service;

import com.jackdking.dao.TransInfoMapper;
import com.jackdking.model.TransInfo;
import com.jackdking.rw.separation.annotation.RWSeparationDBType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransInfoService {

    @Autowired
    private TransInfoMapper transInfoMapper;

    @RWSeparationDBType(value = "ds0")
    public TransInfo queryDs0Record(Long id) {
        return transInfoMapper.get(id);
    }

    @RWSeparationDBType
    public TransInfo queryDs1Record(Long id) {
        return transInfoMapper.get(id);
    }

    public TransInfo queryMapperDs0Record(Long id) {
      return transInfoMapper.queryDsV1(id);
    }

    public TransInfo queryMapperDs1Record(Long id) {
      return transInfoMapper.queryDsV2(id);
    }

}
