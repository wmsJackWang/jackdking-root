package com.example.demo.dao;

import com.example.demo.bean.FileRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by e-shenbin1 on 2018/11/2.
 */
@Mapper
public interface FileResServicemDao {
    int insert(FileRes fileRes);

    int update(Map<String, Object> map);

    List<FileRes> select(@Param("md5") String md5);
}
