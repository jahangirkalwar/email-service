package com.iconsult.internetbanking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDto {
    private String firstName;
    private String lastName;

    private String username;
    private String password;
    private String gender;
    private String address;
    private String email;
    private String phoneNumber;
}
