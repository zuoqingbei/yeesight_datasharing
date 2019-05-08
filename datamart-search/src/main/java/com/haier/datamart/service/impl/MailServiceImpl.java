package com.haier.datamart.service.impl;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.haier.datamart.service.MailService;

/**
 *
 * 邮件服务类 Created by ASUS on 2018/5/5
 *
 * @Authod Grey Wolf
 */

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	// 读取application.properties的内容
	@Value("${mail.fromMail.addr}")
	private String from;

	/**
	 * 发送简单邮件
	 * 
	 * @param to
	 *            接受者
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 */
	@Override
	public boolean sendMail(String[] to, String subject, String content,Map<String,String> attachPath) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			// 如果发给多个人的：
			// mailMessage.setTo("1xx.com","2xx.com","3xx.com");    
			helper.setSubject(subject);
			/*FileSystemResource fileSystemResource = new FileSystemResource(
					new File("D:\76678.pdf"));
			helper.addAttachment("电子发票", fileSystemResource);*/
			helper.setText(content, true);
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发送简单邮件失败");
		}
		return false;
	}
}
