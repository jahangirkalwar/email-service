package com.iconsult.internetbanking.repository;

import com.iconsult.internetbanking.entity.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailOtpRepository extends JpaRepository <EmailOtp,Long> {
    Optional <EmailOtp> findTopByEmailOrderByOtpGenerationTimeDesc (String email);

}
