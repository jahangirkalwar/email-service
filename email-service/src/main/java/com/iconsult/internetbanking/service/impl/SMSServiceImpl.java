package com.iconsult.internetbanking.service.impl;

import com.iconsult.internetbanking.config.TwilioConfig;
import com.iconsult.internetbanking.dto.ApiResponse;
import com.iconsult.internetbanking.dto.SMSOtpGeneratorDto;
import com.iconsult.internetbanking.dto.SMSOtpVerificationDto;
import com.iconsult.internetbanking.entity.SMSOtp;
import com.iconsult.internetbanking.repository.SMSOtpRepository;
import com.iconsult.internetbanking.service.SMSService;
import com.iconsult.internetbanking.util.OtpUtil;
import com.twilio.sdk.creator.api.v2010.account.MessageCreator;
import com.twilio.sdk.resource.api.v2010.account.Message;
import com.twilio.sdk.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SMSServiceImpl implements SMSService {

    @Autowired
    private SMSOtpRepository smsOtpRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(SMSServiceImpl.class);
    private final TwilioConfig twilioConfig;

    @Autowired
    public SMSServiceImpl(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public ApiResponse generate(SMSOtpGeneratorDto smsOtpGeneratorDto) {

        try {
            PhoneNumber to = new PhoneNumber(smsOtpGeneratorDto.getMobileNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String accSid = twilioConfig.getAccountSid();
            String otp = OtpUtil.generateOtp();

            SMSOtp smsOtp = new SMSOtp();
            smsOtp.setOtp(otp);
            smsOtp.setMobileNumber(String.valueOf(to));
            smsOtp.setOtpGenerationTime(LocalDateTime.now());
            smsOtpRepository.save(smsOtp);

            String body = "Dear Customer, we have sent you otp " +otp+". Use this passcode for authentication";
            System.out.println(from);
            MessageCreator message = Message.create(accSid, to, from, body);
            message.execute();
            LOGGER.info("Send Otp {} ", smsOtpGeneratorDto);



        } catch (Exception ex) {
            ex.getMessage();
        }

        return ApiResponse.builder()
                .responseCode("000")
                .responseMessage("otp sms sent to -> "+smsOtpGeneratorDto.getMobileNumber())
                .build();
    }

    @Override
    public ApiResponse verify(SMSOtpVerificationDto smsOtpVerificationDto) {

        String mobileNumber = smsOtpVerificationDto.getMobileNumber();
        String otp = smsOtpVerificationDto.getOtp();

        SMSOtp smsOtp = smsOtpRepository
                .findTopByMobileNumberOrderByOtpGenerationTimeDesc(mobileNumber).orElseThrow(() -> new RuntimeException("Re-generate the otp!"));


        if(smsOtp.getOtp().equals(otp) && Duration.between(smsOtp.getOtpGenerationTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)){
            smsOtpRepository.delete(smsOtp);
            return ApiResponse.builder()
                    .responseCode("000")
                    .responseMessage("otp verified")
                    .build();
        }

        if(!smsOtp.getOtp().equals(otp) && Duration.between(smsOtp.getOtpGenerationTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)){
            return ApiResponse.builder()
                    .responseCode("004")
                    .responseMessage("invalid Otp")
                    .build();

        }

        if(smsOtp.getOtp().equals(otp) && Duration.between(smsOtp.getOtpGenerationTime(),
                LocalDateTime.now()).getSeconds() > (1 * 60 )){
            smsOtpRepository.delete(smsOtp);
            return ApiResponse.builder()
                    .responseCode("004")
                    .responseMessage("otp verification time out!")
                    .build();
        }

        return   ApiResponse.builder().build();
    }
}
