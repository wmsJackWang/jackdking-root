package com.jackdking.service;

import com.jackdking.dao.TransInfoMapper;
import com.jackdking.model.TransInfo;
import jackdking.multids.annotation.DBType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransInfoService {

    @Autowired
    private TransInfoMapper transInfoMapper;

    @DBType(value = "ds0")
    public TransInfo queryDs0Record(Long id) {
        return transInfoMapper.get(id);
    }

    @DBType(value = "ds1")
    public TransInfo queryDs1Record(Long id) {
        return transInfoMapper.get(id);
    }

    public TransInfo queryMapperDs0Record(Long id) {
      return transInfoMapper.queryDs0(id);
    }

    public TransInfo queryMapperDs1Record(Long id) {
      return transInfoMapper.queryDs1(id);
    }

}
