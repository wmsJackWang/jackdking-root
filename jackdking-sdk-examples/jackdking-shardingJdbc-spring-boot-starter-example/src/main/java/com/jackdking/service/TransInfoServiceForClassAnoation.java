package com.jackdking.service;

import com.jackdking.dao.TransInfoDao;
import com.jackdking.model.TransInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransInfoServiceForClassAnoation {

  @Autowired
  private TransInfoDao transInfoDao;

}
