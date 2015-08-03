
package org.firebird.monitor.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import static org.firebird.monitor.util.MonitorUtil.*;

public final class Crypto {

    private static MessageDigest md = null;
    private static final String KEY = "pikachu";

    static {
        try {
            md = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2, hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }

    public static String hashCode(String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest((pwd + KEY).getBytes())));
        }
        return null;
    }

    public static String encript(String src) {
        String result = "";
        int srcAsc = 0;
        int keyPos = 0;
        int keyLen = KEY.length();
        int offset = new Random().nextInt(256);
        result = fillStr(String.format("%1$x", new Integer(offset)), "0", 2, FILL_LEFT);
        for (int srcPos = 0; srcPos < src.length(); srcPos++) {
            srcAsc = (src.charAt(srcPos) + offset) % 255;
            if (keyPos < keyLen) {
                keyPos++;
            } else {
                keyPos = 1;
            }
            srcAsc = srcAsc ^ ((int) KEY.charAt(keyPos - 1));
            result = result.concat(fillStr(String.format("%1$x", new Integer(srcAsc)), "0", 2, FILL_LEFT));
            offset = srcAsc;
        }
        return result;
    }

    public static String decript(String src) {
        try {
            int offset = Integer.parseInt(src.substring(0, 2), 16);
            int srcPos = 2;
            int keyPos = 0;
            String result = "";
            int keyLen = KEY.length();
            int srcAsc;
            int tmpSrcAsc;
            do {
                srcAsc = Integer.parseInt(src.substring(srcPos, srcPos + 2), 16);
                if (keyPos < keyLen) {
                    keyPos++;
                } else {
                    keyPos = 1;
                }
                tmpSrcAsc = srcAsc ^ ((int) KEY.charAt(keyPos - 1));
                if (tmpSrcAsc <= offset) {
                    tmpSrcAsc = 255 + tmpSrcAsc - offset;
                } else {
                    tmpSrcAsc = tmpSrcAsc - offset;
                }
                result = result + ((char) tmpSrcAsc);
                offset = srcAsc;
                srcPos = srcPos + 2;
            } while (src.length() > srcPos);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
