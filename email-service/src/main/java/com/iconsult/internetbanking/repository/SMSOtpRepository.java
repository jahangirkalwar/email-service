package com.iconsult.internetbanking.repository;

import com.iconsult.internetbanking.entity.EmailOtp;
import com.iconsult.internetbanking.entity.SMSOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SMSOtpRepository extends JpaRepository<SMSOtp, Long> {

    Optional <SMSOtp> findTopByMobileNumberOrderByOtpGenerationTimeDesc(String mobileNumber);
}
