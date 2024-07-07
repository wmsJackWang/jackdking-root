package com.jackdking.service;

import com.jackdking.dao.TransInfoMapper;
import com.jackdking.model.TransInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransInfoService {

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

    public TransInfo queryMapperDs2Record(Long id) {
        TransInfo param = new TransInfo();
        param.setOrderid(id);
        return transInfoMapper.queryDsV3(param, id);
    }

}
