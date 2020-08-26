package com.tw.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class MD5Utils {

    public static String getMD5Password(String password,String salt){

//        参数说明
//        第一个参数：原始面
//        第二个参数：盐 salt
//        第三个参数：加盐次数 1 c7f0dc57c5cef977618f656bc75b38ad  2  0b80373f9f998f9ee5e56942caa695b7
//        Md5Hash md5Hash = new Md5Hash(password, salt,1);
//        String pwd_md5 = md5Hash.toString();
//        System.out.println(pwd_md5);
//        第二种使用散列算法方法
        SimpleHash simpleHash = new SimpleHash("md5",password,salt,1);
        return simpleHash.toString();
    }
}
