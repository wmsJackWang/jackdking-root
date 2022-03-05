import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

//		User user = context.getBean(User.class);
//		System.out.println("==========="+user);
//
//		BdTest test = context.getBean(BdTest.class);
//		System.out.println("==========="+test.getUrl());
        Student bean = context.getBean(Student.class);
        System.out.println("================"+bean.getName()+"===================");
    }

}
