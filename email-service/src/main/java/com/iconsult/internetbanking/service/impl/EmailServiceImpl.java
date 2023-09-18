package com.iconsult.internetbanking.service.impl;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.EmailDetails;
import com.iconsult.internetbanking.dto.EmailOtpGeneratorDto;
import com.iconsult.internetbanking.dto.EmailOtpVerificationDto;
import com.iconsult.internetbanking.entity.EmailOtp;
import com.iconsult.internetbanking.repository.EmailOtpRepository;
import com.iconsult.internetbanking.repository.UserRepository;
import com.iconsult.internetbanking.service.EmailService;
import com.iconsult.internetbanking.util.ApiConstants;
import com.iconsult.internetbanking.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailOtpRepository emailOtpRepository;
    @Autowired
    private UserRepository userRepository;

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

    @Override
    public ApiResponse generateOtp(EmailOtpGeneratorDto emailOtpGeneratorDto) {
        String email = emailOtpGeneratorDto.getEmail();
        if(userRepository.existsByEmail(email)){
            String otp = OtpUtil.generateOtp();

            EmailOtp dbOtp= new EmailOtp();
            dbOtp.setOtp(otp);
            dbOtp.setEmail(email);
            dbOtp.setOtpGenerationTime(LocalDateTime.now());
            emailOtpRepository.save(dbOtp);


            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(email)
                    .subject("One Time Password")
                    .messageBody("Your Otp : "+otp)
                    .build();
            sendEmail(emailDetails);

            return ApiResponse.builder()
                    .responseMessage("Otp has been sent")
                    .responseCode("000")
                    .build();

        }

        return ApiResponse.builder()
                .responseCode(ApiConstants.ACCOUNT_NOT_EXISTS_CODE)
                .responseMessage(ApiConstants.ACCOUNT_NOT_EXISTS_MESSAGE)
                .build();

    }

    @Override
    public ApiResponse verifyOtp(EmailOtpVerificationDto emailOtpVerificationDto) {

        String email = emailOtpVerificationDto.getEmail();
        String otp = emailOtpVerificationDto.getOtp();

        EmailOtp dbOtp = emailOtpRepository.findTopByEmailOrderByOtpGenerationTimeDesc(email).orElseThrow(() -> new RuntimeException("Re-generate the otp!"));

        if (dbOtp.getOtp().equals(otp) && Duration.between(dbOtp.getOtpGenerationTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)){
            emailOtpRepository.delete(dbOtp);
            return ApiResponse.builder()
                    .responseCode("000")
                    .responseMessage("otp verified")
                    .build();
        }

        if(!dbOtp.getOtp().equals(otp) && Duration.between(dbOtp.getOtpGenerationTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)){
            return ApiResponse.builder()
                    .responseCode("004")
                    .responseMessage("invalid Otp")
                    .build();

        }

        if(dbOtp.getOtp().equals(otp) && Duration.between(dbOtp.getOtpGenerationTime(),
                LocalDateTime.now()).getSeconds() > (1 * 60 )){
            emailOtpRepository.delete(dbOtp);
            return ApiResponse.builder()
                    .responseCode("004")
                    .responseMessage("otp verification time out!")
                    .build();
        }

        return ApiResponse.builder().build();
    }
}
