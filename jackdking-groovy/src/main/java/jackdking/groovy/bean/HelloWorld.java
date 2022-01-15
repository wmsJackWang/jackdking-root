package jackdking.groovy.bean;

import org.springframework.stereotype.Service;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName HelloWorld
 * @Description TODO
 * @Author jackdking
 * @Date 12/01/2022 11:38 上午
 * @Version 2.0
 **/
@Service
public class HelloWorld {
    public String getWords() { System.out.println("hello world groovy");  return "hello world groovy";}
}
