package com.meijm.jacksoncrypto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meijm.jacksoncrypto.config.MyJacksonAnnotationIntrospector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JacksonCryptoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JacksonCryptoApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(new MyJacksonAnnotationIntrospector());
        return objectMapper;
        // get an object mapper
//        PasswordCryptoContextBuilder
//        ObjectMapper objectMapper = new ObjectMapper();
//// set up a custom crypto context - Defines the interface to the crypto algorithms used
//        PasswordCryptoContext cryptoContext = new PasswordCryptoContext("Pass11111word");
//        // The encryption service holds functionality to map clear to / from encrypted JSON
//        EncryptionService encryptionService = new EncryptionService(objectMapper, cryptoContext);
//// Create a Jackson module and tell it about the encryption service
//        CryptoModule cryptoModule = new CryptoModule().addEncryptionService(encryptionService);
//        // Tell Jackson about the new module
//        objectMapper.registerModule(cryptoModule);
//        return objectMapper;
    }
}