package com.kpicat.webserver.service;

import org.apache.commons.codec.binary.Base64;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.UUID;

public class Common {

    public static String getUniqueId() {
        return uuidToBase64(UUID.randomUUID().toString());
    }

    private static String uuidToBase64(String str) {
        UUID uuid = UUID.fromString(str);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base64.encodeBase64URLSafeString(bb.array());
    }

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
            e.printStackTrace();
        }
        return rt;
    }

    public static boolean isEmpty(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }

        return false;
    }
}
