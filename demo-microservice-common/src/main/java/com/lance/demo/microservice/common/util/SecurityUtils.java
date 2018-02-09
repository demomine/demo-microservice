package com.lance.demo.microservice.common.util;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SecurityUtils {
    private static final int BLOCK_SIZE = 117;
    private static final int OUTPUT_BLOCK_SIZE = 128;
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static LoadingCache<String, Key> rsaPrivateKeyCache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .build(new CacheLoader<String, Key>() {
                @Override
                public Key load(String key) throws Exception {
                    try {
                        byte[] keyBytes = Base64.getDecoder().decode(key);
                        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
                        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
                        return privateKey;
                    } catch (Exception e) {
                        throw new RuntimeException("获取rsa私钥异常", e);
                    }
                }
            });
    private static LoadingCache<String, Key> rsaPublicKeyCache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .build(new CacheLoader<String, Key>() {
                @Override
                public Key load(String key) throws Exception {
                    try {
                        byte[] keyBytes = Base64.getDecoder().decode(key);
                        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                        PublicKey publicKey = keyFactory.generatePublic(keySpec);
                        return publicKey;
                    } catch (Exception e) {
                        throw new RuntimeException("获取rsa公钥异常", e);
                    }
                }
            });

    public static String md5(String src) {
        if (org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return src;
        }

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(src.getBytes("UTF-8"));
            byte[] digest = md5.digest();
            StringBuffer hexString = new StringBuffer();
            String strTemp;
            for (int i = 0; i < digest.length; i++) {
                strTemp = Integer.toHexString((digest[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
                hexString.append(strTemp);
            }
            return hexString.toString();
        } catch (Throwable e) {
            throw new RuntimeException("md5异常", e);
        }
    }

    public static String md5(boolean digital, Object... args) {
        if(args == null || args.length == 0) {
            return "";
        }

        String result = md5(JsonUtils.toJson(args));
        if (digital) {
            result = new BigInteger(result, 16).toString();
        }
        return result;
    }

    public static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64DecodeToByte(String src) {
        return Base64.getDecoder().decode(src);
    }

    public static String base64Encode(String src) {
        if (org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return src;
        }

        try {
            return Base64.getEncoder().encodeToString(src.getBytes("UTF-8"));
        } catch (Throwable e) {
            throw new RuntimeException("base64 encode异常", e);
        }
    }

    public static String base64Decode(String src) {
        if (org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return src;
        }

        try {
            return new String(Base64.getDecoder().decode(src), "UTF-8");
        } catch (Throwable e) {
            throw new RuntimeException("base64 decode异常", e);
        }
    }

    /**
     * aes加密-128位
     * 加密后的长度为(src字节数/16+1)*32
     */
    @Deprecated
    public static String aesEncrypt(String src, String key) {
        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotBlank(key), "密钥不能为空");
        if (org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return src;
        }

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes(DEFAULT_CHARSET));
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return byte2Hex(cipher.doFinal(src.getBytes("UTF-8")));
        } catch (Throwable e) {
            throw new RuntimeException("aes加密异常", e);
        }
    }

    /**
     * aes加密-128位
     * 加密后的长度为比aesEncrypt略小
     */
    public static String aesEncrypt2(String src, String key) {
        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotBlank(key), "密钥不能为空");
        if (org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return src;
        }

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes(DEFAULT_CHARSET));
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return byte2Hex2(cipher.doFinal(src.getBytes("UTF-8")));
        } catch (Throwable e) {
            throw new RuntimeException("aes加密异常", e);
        }
    }

    /**
     * aes解密-128位
     */
    @Deprecated
    public static String aesDecrypt(String src, String key) {
        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotBlank(key), "密钥不能为空");
        if (org.apache.commons.lang3.StringUtils.isBlank(src)) {
            return src;
        }

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes(DEFAULT_CHARSET));
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(hex2Bytes(src)), DEFAULT_CHARSET);
        } catch (Throwable e) {
            throw new RuntimeException("aes解密异常", e);
        }
    }

    public static String aesDecrypt2(String src, String key) {
        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotBlank(key), "密钥不能为空");
        if (StringUtils.isBlank(src)) {
            return src;
        }

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes(DEFAULT_CHARSET));
            keyGen.init(128, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(hex2Bytes2(src)), DEFAULT_CHARSET);
        } catch (Throwable e) {
            throw new RuntimeException("aes解密异常", e);
        }
    }

    /**
     * rsa私钥加密, 密钥使用1024位, PKCS8 with PKCS1Padding
     */
    public static String rsaEncryptByPrivateKey(String src, String key) {
        try {
            Key privateKey = rsaPrivateKeyCache.get(key);
            return rsaEncrypt(src, privateKey);
        } catch (Exception e) {
            throw new RuntimeException("RSA加密异常", e);
        }
    }

    /**
     * rsa公钥解密, 密钥使用1024位, PKCS8 with PKCS1Padding
     */
    public static String rsaDecryptByPublicKey(String src, String key) {
        try {
            Key publicKey = rsaPublicKeyCache.get(key);
            return rsaDecrypt(src, publicKey);
        } catch (Exception e) {
            throw new RuntimeException("RSA解密异常", e);
        }
    }

    /**
     * rsa公钥加密, 密钥使用1024位, PKCS8 with PKCS1Padding
     */
    public static String rsaEncryptByPublicKey(String src, String key) {
        try {
            return rsaEncrypt(src, rsaPublicKeyCache.get(key));
        } catch (Exception e) {
            throw new RuntimeException("RSA加密异常", e);
        }
    }

    /**
     * rsa公钥解密, 密钥使用1024位, PKCS8 with PKCS1Padding
     */
    public static String rsaDecryptByPrivateKey(String src, String key) {
        try {
            return rsaDecrypt(src, rsaPrivateKeyCache.get(key));
        } catch (Exception e) {
            throw new RuntimeException("RSA解密异常", e);
        }
    }

    /**
     * 将byte[] 转换成字符串
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : bytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

    private static String byte2Hex2(byte[] bytes) {
        return base64Encode(bytes);
    }


    /**
     * 将16进制字符串转为转换成字符串
     */
    private static byte[] hex2Bytes(String src) {
        byte[] sourceBytes = new byte[src.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(src.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    private static byte[] hex2Bytes2(String src) {
        return base64DecodeToByte(src);
    }


    private static String rsaEncrypt(String src, Key key) {
        try {
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            rsaCipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] data = src.getBytes("UTF-8");
            int blocks = data.length / BLOCK_SIZE;
            int lastBlockSize = data.length % BLOCK_SIZE;
            byte[] encryptedData = new byte[(lastBlockSize == 0 ? blocks : blocks + 1) * OUTPUT_BLOCK_SIZE];
            for (int i = 0; i < blocks; i++) {
                rsaCipher.doFinal(data, i * BLOCK_SIZE, BLOCK_SIZE,
                        encryptedData, i * OUTPUT_BLOCK_SIZE);
            }
            if (lastBlockSize != 0) {
                rsaCipher.doFinal(data, blocks * BLOCK_SIZE, lastBlockSize,
                        encryptedData, blocks * OUTPUT_BLOCK_SIZE);
            }
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException("RSA加密异常", e);
        }
    }

    private static String rsaDecrypt(String src, Key key) {
        try {
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            rsaCipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(src);
            int blocks = decoded.length / OUTPUT_BLOCK_SIZE;
            ByteArrayOutputStream decodedStream = new ByteArrayOutputStream(decoded.length);
            for (int i = 0; i < blocks; i++) {
                decodedStream.write(rsaCipher.doFinal(decoded, i * OUTPUT_BLOCK_SIZE, OUTPUT_BLOCK_SIZE));
            }
            return new String(decodedStream.toByteArray(), "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("RSA解密异常", e);
        }
    }
}
