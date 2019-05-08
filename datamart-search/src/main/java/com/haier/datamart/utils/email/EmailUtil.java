package com.haier.datamart.utils.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Created by long on 2019/1/23.
 */
@Component
public class EmailUtil {
    private static JavaMailSender javaMailSender;
    private static String FROM;
    @Value("${mail.fromMail.addr}")
    public void setFrom(String from){
        FROM = from;
    }
    @Resource
    public void setMailSender(JavaMailSender mailSender){
        javaMailSender = mailSender;
    }
    public static void sendEmail(List<String> to, List<String> cc, String subject, String content) throws MessagingException {
        System.out.println(content);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(FROM);
        helper.setSubject(subject);
        //接收邮箱
        for (String toAddress : to) {
            helper.addTo(toAddress);
        }

        //抄送邮箱
        if (cc != null) {
            for (String ccAddress : cc) {
                helper.addCc(ccAddress);
            }
        }
        helper.setText(content, true);
        javaMailSender.send(message);
    }
}
