package com.iconsult.internetbanking.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ApiConstants {


    public static final String ACCOUNT_EXISTS_CODE="409";
    public static final String ACCOUNT_EXISTS_MESSAGE="User already registered with this email.";

    public static final String ACCOUNT_NOT_EXISTS_CODE="003";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE="User not registered with this email.";
    public static final String ACCOUNT_CREATION_SUCCESS="001";
    public static final String ACCOUNT_CREATION_MESSAGE="Account has been created successfully.";
    private static final Random random = new Random();
    public static String generateAccountNumber() {
        return "0137" + String.format("%010d", random.nextInt(1000000000));
    }




}
