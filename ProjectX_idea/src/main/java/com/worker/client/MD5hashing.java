package com.worker.client;

import java.security.*;

/**
 * Created by AsmodeusX on 04.11.2016.
 */
public class MD5hashing {

    public String getMD5(String str)
    {
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();

            m.update(str.getBytes());
            byte msgDigest[] = m.digest();
            for (byte aMsgDigest : msgDigest) {
                hexString.append(Integer.toHexString(0xFF & aMsgDigest));
            }

        } catch (NoSuchAlgorithmException e) {
            return str;
        }

        return hexString.toString();
    }
}
