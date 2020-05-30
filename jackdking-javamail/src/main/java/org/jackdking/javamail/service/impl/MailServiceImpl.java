package org.jackdking.javamail.service.impl;

import java.io.File;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.jackdking.javamail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * @author jackdking
 * @date 2018/5/3 22:07
 */
@Component
public class MailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender mailSender;



    @Value("${spring.mail.username}")
    private String mailFrom;

    /**
     * 发送简单邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleEmail(String to,String subject,String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        subject="简单文本邮件";
        content="你好，我是空白";
        to = "jackdking@foxmail.com";//我自己的邮箱
        
        message.setFrom(mailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
        	
            subject="发送html邮件";
            content="<html>\n" +
                    "<body>\n" +
                    "    <h3>hello world !你好，我是空白 ，发送html邮件!</h3>\n" +
                    "</body>\n" +
                    "</html>";

            to = "jackdking@foxmail.com";//我自己的邮箱
            
            
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(mailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送带附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filepath
     */
    @Override
    public void sendFileMail(String to, String subject, String content, String filepath) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        subject="发送带附件的邮件";
        content="你好，我是空白";
        to = "jackdking@foxmail.com";//我自己的邮箱
        filepath="D:\\微信图片_20200524230149.jpg";
        
        
        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(mailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            FileSystemResource file = new FileSystemResource(new File(filepath));
            String fileName = filepath.substring(filepath.lastIndexOf(File.separator));
            helper.addAttachment(fileName,file);

            mailSender.send(mimeMessage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

	@Override
	public void sendPictureMail(String to, String subject, String content, String picturepath) {
		// TODO Auto-generated method stub
        String Id = "jackdking1314";
        content="<html><body>图片邮件：<img src=\'cid:" + Id + "\' ></body></html>";
        String imgPath = "D:\\微信图片_20200524230149.jpg";
        to = "jackdking@foxmail.com";//我自己的邮箱
        
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(to);
            helper.setSubject("这是有图片的邮件");
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(imgPath));
            helper.addInline(Id, res);

            mailSender.send(message); 
        } catch (MessagingException e) {
        	e.printStackTrace();
        }

	}

}
