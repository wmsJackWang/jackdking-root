package com.jackdking.service;

import com.jackdking.model.TransInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class TransInfoServiceTest extends BaseTest{

    @Autowired
    TransInfoService transInfoService;

    @Test
    void queryDs0Record() {
        TransInfo data = transInfoService.queryDs0Record(575491223087742977l);
        log.info("query data:{}", data);
    }

    @Test
    void queryDs1Record() {
        TransInfo data = transInfoService.queryDs1Record(575840641263599616l);
        log.info("query data:{}", data);
    }
}