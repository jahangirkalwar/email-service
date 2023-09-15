package com.iconsult.internetbanking.service;

import com.iconsult.internetbanking.dto.EmailDetails;

public interface EmailService {

    void sendEmail(EmailDetails emailDetails);
}
