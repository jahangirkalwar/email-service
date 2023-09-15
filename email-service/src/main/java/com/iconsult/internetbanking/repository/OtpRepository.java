package com.iconsult.internetbanking.repository;

import com.iconsult.internetbanking.entity.Otp;
import com.iconsult.internetbanking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository <Otp,Long> {
    Otp findByEmail(String email);
}
