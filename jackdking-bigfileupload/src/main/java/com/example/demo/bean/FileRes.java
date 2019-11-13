package com.example.demo.bean;

import java.util.Date;

/**
 * Created by e-shenbin1 on 2018/11/2.
 */
public class FileRes {
    private Integer id;

    private String name;

    private String suffix;

    private String uuid;

    private String path;

    private Integer size;

    private String md5;

    private Integer status;

    private Date createTime;
    
//    create table file_res(id int primary key auto_increment ,name varchar(200),suffix varchar(200),     uuid varchar(400) , path varchar(400) , size int ,md5 varchar(400) , status int ,  createTime date);
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
