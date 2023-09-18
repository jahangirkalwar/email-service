package com.iconsult.internetbanking.service;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.SMSOtpGeneratorDto;
import com.iconsult.internetbanking.dto.SMSOtpVerificationDto;

public interface SMSService {
    public ApiResponse generate(SMSOtpGeneratorDto otpDtoRequest);

    public ApiResponse verify(SMSOtpVerificationDto smsOtpVerificationDto);
}
