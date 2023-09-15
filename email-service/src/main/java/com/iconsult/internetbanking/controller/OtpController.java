package com.iconsult.internetbanking.controller;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ib")
public class OtpController {

    @Autowired
    private UserService userService;

    @PostMapping("/generateOtp")
    public ResponseEntity<ApiResponse> generateOtp(@RequestParam(name = "email") String email){
        return new ResponseEntity<>(userService.sendOtp(email),HttpStatus.OK);

    }


    @PostMapping("/verifyOtp")
    public ResponseEntity<ApiResponse> generateOtp(@RequestParam(name = "email") String email,
                                                   @RequestParam(name = "otp") String otp){
        return new ResponseEntity<>(userService.verifyOtp(email,otp),HttpStatus.OK);

    }


}
