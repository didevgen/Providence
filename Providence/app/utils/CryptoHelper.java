package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptoHelper {

    public static String getDefaultToken() {
        return getToken(256, 36);
    }

    public static String getToken(int numBits, int radix) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(numBits, random).toString(radix);
    }

    public static String md5Password(String input) {
        String md5 = null;
        if(null == input) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            //ignore
        }
        return md5;
    }

}

