package com.iconsult.internetbanking.controller;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.EmailOtpGeneratorDto;
import com.iconsult.internetbanking.dto.EmailOtpVerificationDto;
import com.iconsult.internetbanking.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ib")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/generateOtp")
    public ResponseEntity<ApiResponse> generateOtp(@RequestBody EmailOtpGeneratorDto emailOtpGeneratorDto){
        return new ResponseEntity<>(emailService.generateOtp(emailOtpGeneratorDto),HttpStatus.OK);

    }
    @PostMapping("/verifyOtp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody EmailOtpVerificationDto emailOtpVerificationDto){
        return new ResponseEntity<>(emailService.verifyOtp(emailOtpVerificationDto),HttpStatus.OK);

    }


}
