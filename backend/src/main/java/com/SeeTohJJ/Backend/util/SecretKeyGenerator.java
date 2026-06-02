package com.SeeTohJJ.Backend.util;

import java.security.SecureRandom;
import java.util.Base64;


// This class generates a random secret key for JWT signing, used in application.yaml
public class SecretKeyGenerator {

    public static void main(String[] args) {

        byte[] key = new byte[32];

        SecureRandom random = new SecureRandom();
        random.nextBytes(key);

        String secret = Base64.getEncoder().encodeToString(key);

        System.out.println(secret);
    }
}
