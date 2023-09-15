package com.iconsult.internetbanking.controller;

import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.UserDto;
import com.iconsult.internetbanking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ib")
public class UserController {

    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register (@RequestBody UserDto request){
        return new ResponseEntity<>(userService.register(request), HttpStatus.OK);
    }

}
