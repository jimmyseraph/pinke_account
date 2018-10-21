package com.pinke.liudao.pinke_account.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {
    public static String hashDigest(String content, String algorithm) throws
            NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] bs = messageDigest.digest(content.getBytes("utf-8"));
        return StringUtils.byteArray2HexString(bs);
    }

}
