package com.pinke.liudao.pinke_account.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;

public class TokenUtils {
    public static String genKeyString(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static SecretKey genSecretKey(String keyString){
        return new SecretKeySpec(keyString.getBytes(),"AES");
    }

    public static String createToken(SecretKey key, String username, Date expire){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder builder = Jwts.builder()
                .setAudience(username)
                .setExpiration(expire)
                .signWith(signatureAlgorithm, key);
        return builder.compact();
    }

    public static void main(String[] args) {
        SecretKey key = genSecretKey(genKeyString());
        System.out.println(createToken(key, "liudao", new Date()));
    }
}
