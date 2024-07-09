package com.jackdking.service;

import com.jackdking.model.TransInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class TransInfoServiceTest extends BaseTest {

  @Autowired
  TransInfoService transInfoService;

  @Test
  void queryServiceDs0Record() {
    TransInfo data = transInfoService.queryDs0Record(575491223087742977l);
    log.info("query data:{}", data);
  }

  @Test
  void queryServiceDs1Record() {
    TransInfo data = transInfoService.queryDs1Record(575840641263599616l);
    log.info("query data:{}", data);
  }


  @Test
  void queryMapperDs0Record() {
    TransInfo data = transInfoService.queryMapperDs0Record(575491223087742977l);
    log.info("query data:{}", data);
  }

  @Test
  void queryMapperDs1Record() {
    TransInfo data = transInfoService.queryMapperDs1Record(575840641263599616l);
    log.info("query data:{}", data);
  }
}
