package com.iconsult.internetbanking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailOtpVerificationDto {

    @JsonProperty("email")
    private String email;

    @JsonProperty("otp")
    private String otp;
}
