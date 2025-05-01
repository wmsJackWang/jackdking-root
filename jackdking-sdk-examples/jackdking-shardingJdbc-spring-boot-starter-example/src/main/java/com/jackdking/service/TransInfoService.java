package com.jackdking.service;

import com.jackdking.dao.TransInfoDao;
import com.jackdking.model.TransInfo;
import com.jackdking.repository.TransInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TransInfoService {

  @Autowired
  private TransInfoRepository repository;

  public void createOrder(TransInfo transInfo) {
    repository.insert(transInfo);

  }

}
