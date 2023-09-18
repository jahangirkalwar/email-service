package com.iconsult.internetbanking.service;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.EmailDetails;
import com.iconsult.internetbanking.dto.EmailOtpGeneratorDto;
import com.iconsult.internetbanking.dto.EmailOtpVerificationDto;

public interface EmailService {

    void sendEmail(EmailDetails emailDetails);

    ApiResponse generateOtp(EmailOtpGeneratorDto emailOtpGeneratorDto);

    ApiResponse verifyOtp(EmailOtpVerificationDto emailOtpVerificationDto);
}
