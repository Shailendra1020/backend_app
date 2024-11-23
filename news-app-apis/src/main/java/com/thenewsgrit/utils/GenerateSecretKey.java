package com.thenewsgrit.utils;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;

public class GenerateSecretKey {
    public static void main(String[] args) {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String base64Key = Encoders.BASE64.encode(keyBytes);
        System.out.println("Base64-encoded secret key: " + base64Key);
    }
}
