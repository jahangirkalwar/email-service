package com.iconsult.internetbanking.service.impl;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.EmailDetails;
import com.iconsult.internetbanking.dto.UserDto;
import com.iconsult.internetbanking.entity.Otp;
import com.iconsult.internetbanking.entity.User;
import com.iconsult.internetbanking.repository.OtpRepository;
import com.iconsult.internetbanking.repository.UserRepository;
import com.iconsult.internetbanking.service.EmailService;
import com.iconsult.internetbanking.service.UserService;
import com.iconsult.internetbanking.util.ApiConstants;
import com.iconsult.internetbanking.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private EmailService emailService;


    @Override
    public ApiResponse register(UserDto request) {



        if(userRepository.existsByEmail(request.getEmail())){
            return ApiResponse.builder()
                    .responseCode(ApiConstants.ACCOUNT_EXISTS_CODE)
                    .responseMessage(ApiConstants.ACCOUNT_EXISTS_MESSAGE)
                    .build();
        }

        User newUser = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(request.getPassword())
                .gender(request.getGender())
                .address(request.getAddress())
                .accountNumber(ApiConstants.generateAccountNumber())
                .email(request.getEmail())
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(newUser);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(request.getEmail())
                .subject("Account Registration")
                .messageBody("You account has been successfully registered!")
                .build();
        emailService.sendEmail(emailDetails);

        return ApiResponse.builder()
                .responseCode(ApiConstants.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(ApiConstants.ACCOUNT_CREATION_MESSAGE)
                .build();

    }

    @Override
    public ApiResponse sendOtp(String email) {

        if(userRepository.existsByEmail(email)){

            String otp = OtpUtil.generateOtp();

            Otp dbOtp= new Otp();
            dbOtp.setOtp(otp);
            dbOtp.setEmail(email);
            dbOtp.setOtpGenerationTime(LocalDateTime.now());
            otpRepository.save(dbOtp);


            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(email)
                    .subject("One Time Password")
                    .messageBody("Your Otp : "+otp)
                    .build();
            emailService.sendEmail(emailDetails);

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


}
