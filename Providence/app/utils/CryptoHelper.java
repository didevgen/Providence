package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class CryptoHelper {

    public static String getDefaultToken() {
        return getToken(256, 36);
    }

    public static String getToken(int numBits, int radix) {
        SecureRandom random = new SecureRandom();
        return new BigInteger(numBits, random).toString(radix);
    }

}

