package com.devtrack.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class OtpGenerator {
    public String generateToken() {
        int code = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
        return String.format("%06d", code);
    }
}