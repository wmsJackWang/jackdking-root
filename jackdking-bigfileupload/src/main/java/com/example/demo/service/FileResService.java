package com.example.demo.service;

import com.example.demo.bean.FileRes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by e-shenbin1 on 2018/11/2.
 */

public interface FileResService {

    int insert(FileRes fileRes);

    int update(Map<String, Object> map);

    List<FileRes> select(String md5);
}
