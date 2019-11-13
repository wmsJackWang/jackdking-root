package com.example.demo.service.impl;

import com.example.demo.bean.FileRes;
import com.example.demo.dao.FileResServicemDao;
import com.example.demo.service.FileResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by e-shenbin1 on 2018/11/2.
 */
@Service
public class FileResServiceImpl implements FileResService {

    @Autowired
    private FileResServicemDao fileResServicemDao;


    @Override
    public int insert(FileRes fileRes) {
        return fileResServicemDao.insert(fileRes);
    }

    @Override
    public int update(Map<String, Object> map) {
        return fileResServicemDao.update(map);
    }

    @Override
    public List<FileRes> select(String md5) {
        return fileResServicemDao.select(md5);
    }
}
