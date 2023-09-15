package com.iconsult.internetbanking.service;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.UserDto;

public interface UserService {

    ApiResponse register (UserDto request);

    ApiResponse sendOtp(String email);

    ApiResponse verifyOtp(String email, String otp);
}
