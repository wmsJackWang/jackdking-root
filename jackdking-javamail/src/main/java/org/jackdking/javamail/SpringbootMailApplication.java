package org.jackdking.javamail;

import org.jackdking.javamail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class SpringbootMailApplication  implements ApplicationRunner
{
	@Autowired
	IMailService mailService;
	
    public static void main( String[] args )
    {
        SpringApplication.run(SpringbootMailApplication.class, args);
    }
    
    
    //启动应用后直接发送邮件
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		mailService.sendSimpleEmail(null, null, null);
		mailService.sendHtmlMail(null, null, null);
		mailService.sendFileMail(null, null, null,null);
		mailService.sendPictureMail(null, null, null,null);
		
	}
}
