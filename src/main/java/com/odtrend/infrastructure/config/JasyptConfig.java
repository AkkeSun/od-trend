package com.odtrend.infrastructure.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    public static final String ENCRYPT_KEY = "od-trend";
    public static final String ENCRYPT_ALGORITHM = "PBEWithMD5AndDES";

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(ENCRYPT_KEY);
        config.setAlgorithm(ENCRYPT_ALGORITHM);
        config.setPoolSize("1");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
}