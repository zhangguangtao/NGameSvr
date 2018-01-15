package com.game.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtil {

	public static final String RSA_PADDING_MODE = "RSA";

	private static void test() throws Exception {
		String data = "hello world";
		KeyPair keyPair = genKeyPair(1024);

		// 获取公钥，并以base64格式打印出来
		PublicKey publicKey = keyPair.getPublic();
		String publicKeyStr = new String(Base64.encode(publicKey.getEncoded()));
		System.out.println("公钥：" + publicKeyStr);

		// 获取私钥，并以base64格式打印出来
		PrivateKey privateKey = keyPair.getPrivate();
		String privateKeyStr = new String(Base64.encode(privateKey.getEncoded()));
		System.out.println("私钥：" + privateKeyStr);

		PublicKey publicKey2 = RSAUtil.generateRSAPublicKey(Base64.decode(publicKeyStr));

		byte[] bytes = RSAUtil.encrypt(data.getBytes(), publicKey2);
		PrivateKey privateKey2 = RSAUtil.generateRSAPrivateKey(Base64.decode(privateKeyStr));
		byte[] bytes2 = RSAUtil.decrypt(bytes, privateKey2);
		System.out.println(new String(bytes2));
	}

	// 生成密钥对
	public static KeyPair genKeyPair(int keyLength) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		return keyPairGenerator.generateKeyPair();
	}

	/**
	 * 根据字节流产生公钥
	 *
	 * @param key
	 * @return 公钥
	 */
	public static PublicKey generateRSAPublicKey(byte[] key) {
		try {
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(key);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			return pubKey;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * RSA加密运算。
	 *
	 * @param data
	 * @param publicKey
	 * @return 加密结果
	 */
	public static byte[] encrypt(byte[] data, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_PADDING_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据字节流产生私钥
	 *
	 * @param key
	 * @return 私钥
	 */
	public static PrivateKey generateRSAPrivateKey(byte[] key) {
		try {
			PKCS8EncodedKeySpec pkcs8keyspec = new PKCS8EncodedKeySpec(key);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8keyspec);
			return priKey;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] decrypt(byte[] data, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_PADDING_MODE);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
