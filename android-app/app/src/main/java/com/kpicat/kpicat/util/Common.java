package com.kpicat.kpicat.util;

import android.text.TextUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static String getMd5Hash(String s) {
        String rt = "";
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(s.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch(Exception e) {
        }
        return rt;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        return false;
    }

    public final static int parseFontSize(int fontSize) {
        return fontSize == 0 ? 20 : fontSize;
    }

    public final static int parseChartHeight(int height) {
        return height == 0 ? 600 : height;
    }

    public final static String parseBgColor(String bgColor) {
        if (bgColor == null || bgColor.isEmpty()) {
            return "#FFFFFF";
        }
        return bgColor;
    }

    public final static String parseFontColor(String fontColor) {
        if (fontColor == null || fontColor.isEmpty()) {
            return "#000000";
        }
        return fontColor;
    }

    public final static String parseUnixTime(long t) {
        if (t == 0) {
            return "";
        } else {
            Date time= new Date(t * 1000L);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(time);
            return formattedDate;
        }
    }
}
