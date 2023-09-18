package com.iconsult.internetbanking.controller;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.SMSOtpGeneratorDto;
import com.iconsult.internetbanking.dto.SMSOtpVerificationDto;
import com.iconsult.internetbanking.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ib")
public class SMSController {

    @Autowired
    private SMSService smsService;

    @PostMapping("/generateSmsOtp")
    public ResponseEntity<ApiResponse> generateOtp(@RequestBody SMSOtpGeneratorDto smsOtpGeneratorDto){
        return ResponseEntity.ok(smsService.generate(smsOtpGeneratorDto));
    }

    @PostMapping("/verifySmsOtp")
    public ResponseEntity<ApiResponse> verifyOtp (@RequestBody SMSOtpVerificationDto smsOtpVerificationDto){
        return ResponseEntity.ok(smsService.verify(smsOtpVerificationDto));
    }
}
