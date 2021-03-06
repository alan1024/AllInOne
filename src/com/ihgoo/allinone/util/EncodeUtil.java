package com.ihgoo.allinone.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * Created by ihgoo on 2015/6/16.
 */
public class EncodeUtil {

    /**
     * @param str md5加密
     * @return
     */
    public static String md5s(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        StringBuffer hexString = new StringBuffer();

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            int length = hash.length;
            for (int i = 0; i < length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexString.toString();
    }

    public static String encrypt(String input) throws Exception {
        return base64Encode(desEncrypt(input.getBytes()));
    }

    private static String base64Encode(byte[] s) {
        if (s == null)
            return null;

        return Base64.encodeBytes(s);
    }

    /**
     * DES密钥
     */
    private static final String private_key = "i*_uka*ld(_l);jsl;#k*&a";

    private static byte[] desEncrypt(byte[] plainText) throws Exception {
        SecureRandom sr = new SecureRandom();
        byte rawKeyData[] = private_key.getBytes();
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);
        byte data[] = plainText;
        byte encryptedData[] = cipher.doFinal(data);
        return encryptedData;
    }

}
