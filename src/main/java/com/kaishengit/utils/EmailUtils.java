package com.kaishengit.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by loveoh on 2016/12/16.
 */
public class EmailUtils {

    private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    public static void sendHtmlEmail(String toAddress,String subject,String context){
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(Config.get("email.smpt"));
        htmlEmail.setSmtpPort(Integer.valueOf(Config.get("email.port")));
        htmlEmail.setAuthentication(Config.get("email.username"),Config.get("email.password"));
        //解决发邮件中文乱码的问题
        htmlEmail.setCharset("utf-8");
        try {
            htmlEmail.setFrom("kaishengit@126.com");
            htmlEmail.setHtmlMsg(context);
            htmlEmail.setSubject(subject);
            htmlEmail.addTo(toAddress);
            htmlEmail.send();
        } catch (EmailException e) {
            logger.error("给{}发送邮件失败",toAddress);
            throw new RuntimeException("给"+toAddress+"发送邮件失败");

        }

    }


}
