package com.sekarre.springdataandhibernatecourseinterceptors.services;

public interface EncryptionService {

    String encrypt(String freeText);

    String decrypt(String encryptedText);
}
