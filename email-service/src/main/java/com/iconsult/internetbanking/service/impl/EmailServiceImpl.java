package com.iconsult.internetbanking.service.impl;

import com.iconsult.internetbanking.dto.EmailDetails;
import com.iconsult.internetbanking.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${email.sender.username}")
    private String emailSender;
    @Override
    public void sendEmail(EmailDetails emailDetails) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailSender);
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setSubject(emailDetails.getSubject());
            mailMessage.setText(emailDetails.getMessageBody());
            mailSender.send(mailMessage);

            System.out.println("mail sent successfully");
        }catch (MailException e){
            throw new RuntimeException(e);
        }

    }
}
