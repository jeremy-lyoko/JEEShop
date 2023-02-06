package com.Jeremy.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    public static void sendMail(String email, String emailMsg)
            throws AddressException, MessagingException {
        //创建与邮件服务器会话对象Session
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.host", "smtp.163.com");
        props.setProperty("mail.smtp.auth", "true");// 指定验证为true


        //创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("13718703372@163.com", "33a124M77587");
            }
        };

        Session session = Session.getInstance(props, auth);
        //创建一个Message相当于是邮件内容
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("13718703372@163.com")); // 设置发送者

        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

        message.setSubject("用户激活");
        // message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

        message.setContent(emailMsg, "text/html;charset=utf-8");

        //创建Transport用于将邮件发送

        Transport.send(message);

    }
}
