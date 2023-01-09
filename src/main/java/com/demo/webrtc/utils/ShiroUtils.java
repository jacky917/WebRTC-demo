//package com.demo.webrtc.utils;
//
//import com.google.common.base.Preconditions;
//import com.google.common.base.Strings;
//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.apache.shiro.crypto.hash.SimpleHash;
//
//import java.util.Random;
//
///**
// * shiro工具類
// */
//public class ShiroUtils {
//
//    /**
//     * 加鹽參數
//     */
//    public final static String hashAlgorithmName = "MD5";
//
//    /**
//     * 循環次數
//     */
//    public final static int hashIterations = 1024;
//
//    /**
//     * shiro密碼加密工具類
//     *
//     * @param credentials 密碼
//     * @param saltSource 密碼鹽
//     * @return 返回加密字串
//     */
//    public static String md5(String credentials, String saltSource) {
//        Md5Hash salt = new Md5Hash(saltSource);
//        System.out.println(credentials);
//        System.out.println(new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString());
//        System.out.println("============password==========");
//        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
//    }
//
//    /**
//     * 獲取隨機鹽值
//     * @param length 長度
//     * @return 返回鹽值
//     */
//    public static String getRandomSalt(int length) {
//        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            int number = random.nextInt(base.length());
//            sb.append(base.charAt(number));
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 驗證密碼是否一致
//     * @param username 用戶名
//     * @param password 密碼
//     * @param salt 密碼鹽
//     * @param md5cipherText 密文
//     * @return 返回結果
//     */
//    public static boolean checkMd5Password(String username,String password,String salt,String md5cipherText){
//    	Preconditions.checkArgument(!Strings.isNullOrEmpty(username),"username不能為空");
//        Preconditions.checkArgument(!Strings.isNullOrEmpty(password),"password不能為空");
//        Preconditions.checkArgument(!Strings.isNullOrEmpty(md5cipherText),"md5cipherText不能為空");
//        Md5Hash credentialsSalt = new Md5Hash(salt);
//        //通用散列加密方法
//        SimpleHash hash = new SimpleHash(ShiroUtils.hashAlgorithmName, password, credentialsSalt, ShiroUtils.hashIterations);
//        return md5cipherText.equals(hash.toHex());
//    }
//}
