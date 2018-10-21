package com.pinke.liudao.pinke_account.utils;

public class StringUtils {
    public static String byteArray2HexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
            int c = bytes[i]<0 ? 256 + bytes[i] : bytes[i];
            String tmp = Integer.toHexString(c);
            tmp = tmp.length() == 1 ? "0"+tmp : tmp;
            sb.append(tmp);
        }
        return new String(sb);
    }

    public static byte[] hexString2ByteArray(String source) {
        if(source.length() % 2 != 0) {
            source = "0" + source;
        }
        int len = source.length() / 2;
        byte[] bytes = new byte[len];
        for(int i = 0; i < len; i++) {
            int c = Integer.parseInt(source.substring(i*2, i*2+2),16);
            bytes[i] = (byte)c;
        }
        return bytes;
    }
}
