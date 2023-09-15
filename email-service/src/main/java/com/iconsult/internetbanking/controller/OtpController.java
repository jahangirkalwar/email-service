package com.iconsult.internetbanking.controller;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.EmailDetails;
import com.iconsult.internetbanking.service.EmailService;
import com.iconsult.internetbanking.service.UserService;
import com.iconsult.internetbanking.util.OtpUtil;
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


}
