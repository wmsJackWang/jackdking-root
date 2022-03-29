package aplication;

import core.utils.SpringContextUtils;
import domain.Student;
import domain.Teacher;
import groovy.util.logging.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName Application
 * @Description TODO
 * @Author jackdking
 * @Date 04/03/2022 7:17 下午R
 * @Version 2.0
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"core"})
@Slf4j
public class Application implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

//		User user = context.getBean(User.class);
//		System.out.println("==========="+user);
//
//		BdTest test = context.getBean(BdTest.class);
//		System.out.println("==========="+test.getUrl());
//        System.out.println("================"+bean.getName()+"===================");

    }

    @Override
    public void run(String... args) throws Exception {
        Student student = SpringContextUtils.getBean("student", Student.class);
        System.out.println(student.toString());


        Teacher teacher = SpringContextUtils.getBean("teacher", Teacher.class);
        System.out.println(teacher.toString());
    }
}
