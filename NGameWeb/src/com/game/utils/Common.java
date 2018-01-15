package com.game.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by zgt on 2018/1/10.
 */

public class Common {
    public final static String BASE64PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQClAqx8gcknssOiZzJQDFKp6echAULMwGpLcG9BjrFax031gzBHr2VIWl3zcMCDWWtM5SPFxGLvPiUtsKf0rUya+d/+KDdu1mnW4qAiy8mMmnRrS9pvEjsv10V+FjIteH1Eu+OMdrBkmJc4Y/8uMOwdQEdMMqykqFA6jnxBhyW22QIDAQAB";
    public final static String BASE64PrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKUCrHyBySeyw6JnMlAMUqnp5yEBQszAaktwb0GOsVrHTfWDMEevZUhaXfNwwINZa0zlI8XEYu8+JS2wp/StTJr53/4oN27WadbioCLLyYyadGtL2m8SOy/XRX4WMi14fUS744x2sGSYlzhj/y4w7B1AR0wyrKSoUDqOfEGHJbbZAgMBAAECgYAQYs+WHZ1IH+xleDH62P5seRnGoUVs576mdyfVLsJV7WzEcJ6ev8AjCzZBMnUrlfmdzTNcNEBO2bD/dId5OKxenn2Y/wr6GBD2PZfLjbHuvCkTd51RhK3AubkcwMwJP/Cpn/F3fEDzaj4f5A70SbHtPQAHp2uRf/ZgJ7TZPxcOAQJBANyXuT7b2KVgEXz51h+31qcsSzRfHlsqxzlwXSi3Xx6Gyc4eGpXXV8jib+hAEPHMz6QTKypdrDbOn2ndBjtWeAkCQQC/fwuc46UtTI1IJWOV+4IZ/vmD3IKDzGFmN6+4vPe76dS/tHlc+cpxja191mM9TetP121dxv0GW/75oJw5EtxRAkEA2sxzJXISVRYQwpFcXWGPO4mnnm6qQWsQ6FwXSwd3BWRUTml1nSJXJB3VOdwx8mRfoDKTIhZ/dm1DmVFtmPWPYQJAJK6ngNQyrey+G5Xd77sP/v7SuSmibrDV74i3RoIcSojOUjK2FNNcrkffdf19YR7t9otjzdTyXyd8PkE/68brIQJACKKXfkWdj8choxgm9La2rRoTuBvaBz22h5fNE2RnkoGwPTFA2EyOh4XHtAFLnGDUC1VWz1xWyT0bTN5Tn0LPXg==";

    public final static String AESKEY = "abchfgtyuikjhgfr";
    
    public final static String APP_KEY = "abc456321";

    public static String encryptAESJson(String json){
        String data=null;
        try {
            data=  AES.Encrypt(json,AESKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String decryptAESJson(String json){
        String data=null;
        try {
            data=  AES.Decrypt(json,AESKEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }




    /**
     * 加密
     * @param json
     * @return
     */
    public static String encryptJson(String json){
        PublicKey publicKey2 = RSAUtil.generateRSAPublicKey(Base64.decode(BASE64PublicKey));
        byte[] bytes = RSAUtil.encrypt(json.getBytes(), publicKey2);
        return Base64.encode(bytes);
    }

    /**
     * 解密
     * @param base64Str
     * @return
     */
    public static String decryptJson(String base64Str){
        PrivateKey privateKey=  RSAUtil.generateRSAPrivateKey(Base64.decode(BASE64PrivateKey));
        byte[] bytes = RSAUtil.decrypt(Base64.decode(base64Str),privateKey);
        return new String(bytes);
    }

}
