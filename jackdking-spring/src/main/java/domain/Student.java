package domain;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName Student
 * @Description TODO
 * @Author jackdking
 * @Date 04/03/2022 7:23 下午
 * @Version 2.0
 **/
public class Student {
    private String name = "zhangsan";
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
