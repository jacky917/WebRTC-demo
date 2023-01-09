package com.demo.webrtc.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public interface CryptoUtils {

    final String key = "0YVUpayReqL42F74";
    final String iv = "1ExkRPT5nhsYxaQi";

    /**
     * AES CBC 加密
     * @param message 需要加密的字符串
     * @return  返回加密后密文，编码为base64
     */
    public static String encryptCBC(String message) {

        final String cipherMode = "AES/CBC/PKCS5Padding";
        final String charsetName = "UTF-8";

        try {
            byte[] content = new byte[0];
            content = message.getBytes(charsetName);
            //
            byte[] keyByte = CryptoUtils.key.getBytes(charsetName);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
            //
            byte[] ivByte = CryptoUtils.iv.getBytes(charsetName);
            IvParameterSpec ivSpec = new IvParameterSpec(ivByte);

            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] data = cipher.doFinal(content);
            final Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES CBC 解密
     * @param messageBase64 密文，base64編碼
     * @return 解密後資料
     */
    public static String decryptCBC(String messageBase64) {

        final String cipherMode = "AES/CBC/PKCS5Padding";
        final String charsetName = "UTF-8";
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            byte[] messageByte = decoder.decode(messageBase64);

            byte[] keyByte = CryptoUtils.key.getBytes(charsetName);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");

            byte[] ivByte = CryptoUtils.iv.getBytes(charsetName);
            IvParameterSpec ivSpec = new IvParameterSpec(ivByte);

            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] content = cipher.doFinal(messageByte);
            return new String(content, charsetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
