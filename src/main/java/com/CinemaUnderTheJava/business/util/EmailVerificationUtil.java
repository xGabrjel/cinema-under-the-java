package com.cinemaUnderTheJava.business.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
@AllArgsConstructor
public class EmailVerificationUtil {

    private final JavaMailSender javaMailSender;

    public void sendVerificationEmail(String sendTo, String url) throws MessagingException, UnsupportedEncodingException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.getDefault());
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        String mailContent = resourceBundle.getString("registration.message1")
                + "<a href='" + url + "'>" + url + "</a>"
                + resourceBundle.getString("registration.message2");
        messageHelper.setTo(sendTo);
        messageHelper.setFrom(resourceBundle.getString("registration.from"), resourceBundle.getString("registration.personal"));
        messageHelper.setSubject(resourceBundle.getString("registration.subject"));
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
