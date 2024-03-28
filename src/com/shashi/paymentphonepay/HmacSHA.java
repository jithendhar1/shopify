package com.shashi.paymentphonepay;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA {

	public static String computeHmacSha256Signature(String data, String key) throws Exception {
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);

			byte[] hash = sha256_HMAC.doFinal(data.getBytes());
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new Exception("Error computing HMAC SHA-256 signature: " + e.getMessage());
		}
	}
}
