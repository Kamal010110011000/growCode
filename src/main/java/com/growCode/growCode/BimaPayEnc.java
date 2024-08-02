// package com.test;
package com.growCode.growCode;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// import jcifs.util.Base64;

public class BimaPayEnc {

	public static void main(String args[]) throws Exception {

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("email", "kamal.stage@bimapay.in");
		orderRequest.put("password", "h?rA3#jsA54p");
		orderRequest.put("userType", "partner");
		System.out.println("create token request payload: " + orderRequest.toString());

		String data = null;
		try {
			data = new ObjectMapper().writeValueAsString(orderRequest);
		} catch (JsonProcessingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		SecureRandom secureRandom = SecureRandom.getInstance("NativePRNG");
        
		// byte[] array = new byte[16];
		// need 16 byte iv. Using Random.nextBytes gives wierd characters on converting it into string and getBytes on that string changes length of byte array
		// secureRandom.nextBytes(array);
		// String ivStr = new String("mustabe16byte_iv");

		// byte[] array2 = new byte[32];
		// need 32 byte key. Using Random.nextBytes gives wierd characters on converting it into string and getBytes on that string changes length of byte array
		// secureRandom.nextBytes(array2);

		// String preKey = new String("thisisa16bytekeyform16charstring");
		// Directly converting bytes to string and back changing byte array length. So created method to create random string of desired length
		String ivStr = randomAlphanumericString(16);
		String preKey = randomAlphanumericString(32);
        String key = encode(preKey);
        String iv = encode(ivStr);

		System.out.println("key => " + key + "\n iv => "+ ivStr);
		orderRequest = createBMTokenRequest(data, key, iv);
		System.out.println("request after createBMTokenRequest : " + orderRequest + "\n");

		// String keyEnc = "Yc9Jd5vfKz4ZcP6WkB0N38uko/hrsiMZXAEFaPauNxtbCnmpAxmx3OHVWyVM8QSgNLastUkOB/obeLJ9CfIj2Kyy0ggAQw1sXb12M+Bpr+MTEQUoIhjjEcQs9uyByQdf7hxPRVo9LteoV50gGixKvt+ZUTjNBgKeoLoOLz+ifwTCBbQldNKlWqtRoh6+HwAk2a4AqNIxzgr2PeI/H8LHE4cwnVu8c3EDQbLjL3HnDKhLBbjLFNRmBPiCiOHP5jj7GFEROwnzks7zrpg3KHqrdYIXiNs7OyN0Vlck5KIq6PcKUW0MnVOMuXZqv5Y2QPt7yj2YNbv9aPsdn4aj7WUEYg==";

		JSONObject decryptData = null;
		JSONObject orderResponse = new JSONObject();
		// example response copied from postman
		orderResponse.put("data", "eyJ2YWx1ZSI6IjB2Y0RCcU5lalRaSDRSalpXcG1Nb3Bna0d5RjhCc1RNZ0FJdTBiOTZJS1dIZlpqb21ZSmd5aGtZTC93aTdsZjR4MTdQZ1J3b3ptdFhNSmEwNTltNHozOC9QVjA1cS8zT0RXVk1YSUFiTDlzYjA4LzBxbUxLZ0NlaTQ3UFk3Vis3djlsTFNkZ3RncWI4L0JSSm1lUTRKc3RlbUZLRjVPMDRpd21HMGZsSTZVdzAxdUxzN01pZFdjL1ExMFY4VU9oeUJ0djdNeGRGZStmcUI1K3BEUkFTL3BCV1RRNVBHOUI0dC9UWWY2Y2YvQUdKcUhtN1pVSUgraWxDMFpLTURsSkhpOHZjZTI2WGtGR0pDdnJkemZaZEZTcWsrbVZzcDdwb1VWMW9LRUwwbkdkUWdNWGgyTDBQd2NwY0JNZk5KRkpqa3d4N1BZQWR5M0toRU0wTUIvQzlsMStuMGRMbHdVK0g5QXZqT2c3eG45Ykk3a3FyblBnVExlVUp4SXFLakR1MWhLT0JyNnQxeHFDdGxiaVZieitpeTR5SUVkYzV3T2dYeTNGQ01Yc0EzaDJFMmxQbHFrYTNXQjJzQTI5L0lOa0phSVRaUnJjMFpxZWpIb0trYkNiZ1MvTitNNHpiS0dFQjZKZ25TSUhDV0VNVlE5dFVKWmdzT2QxWFA1WnExaWdrZHN1b282WkxsSW1zS1dmdnBWOFRiOTRKdWpvT3RITnNSN3U4djBmM1NWQ2U5QmxISkRaaTY3ZjlHM2s3N2R6K0xsMFdmYS9XaUw4NE43UVJ6YUd0M3gwNVFQL3o2UzhtWlBvWjJGaG4raUE9IiwibWFjIjoiN2YzN2ZhYTUzZjYzYzBjYjgzYWEzNjcwMjBlMzE5MWY2YTIzOGQ5ZDc2Y2IwZGMyODFlOTg0MmUzOTc5MWNlNSJ9");
		orderResponse.put("key", "IkQ53bpNXKL4hiCAY20P57KO127O6la/t0czQWzbKQUrD0OPZxltXnZhcvjUs2dNAY7dT0Mdi+Q0cEDmBpT7ViS/FMEuCR12tKjj1MG3o7y5nW/Prx1SpTEt4El+1l3GQNG9qRITNyKStthFESnYxhRJKFoX5W4c0vZe+EuyO9ExFtn/o994kbdDi8X9+65eFIdv4PSfhDCbB2JqvcMidp/nBgUcI0HVVigTY1+wSFE+0XXH7IWyWHoYTh+aLLIcpAmP/ne0aWdXI1AiNHUDNml7uOKN+1omy1VpnLE78pg30pvuITRqfPem/eCkNMjzVe74RZgE1kQv5i0jW+3/yg==");
		decryptData = decryptResponse(orderResponse);
		System.out.println("response after decryption : " + decryptData.toJSONString() + "\n");
	}

    public static JSONObject decryptResponse(JSONObject orderResponse) throws Exception{
		//this key only decrypt response. this can not decrypt request data which is encrypted
		String privateKey = "MIIEpAIBAAKCAQEAuUGOKednc62eUsbDDuQeQe0Pxa1yYl2YJaDzvfk0oporMAlg\n"
				+ "OT5FxO76EsPgqo+cQjTCwSkqMg2CcAxaBaS/mCfiSE9Wp0cUdv1zXdeTkU2HQXQV\n"
				+ "YZkDC/n0nTDgkVbsT1lpET2NqBiPl3fAVTvPAMB1zo6I0L85HJAOhWs9lLvsH39b\n"
				+ "OYlcjM4LuybenNl9Sl5Rn/PrlhnQ3l/eHt8giRMuOMn2SEtI2wC83XiPoTEwt0IC\n"
				+ "3zk+kdruE2kb4lE8gTMbT2rvuexYjpR9ww4UzxjFV7dgmAx8aS0IDp+0RSk3WoaQ\n"
				+ "3K0fuWAhmjL1nOzLJUCduvwajUvod9pf6xwcUQIDAQABAoIBAGCQdDsRfNJGcJ1z\n"
				+ "L5+mdsMkf7EprNLrOHB8yjB/ItmTBOoAKSENfa6eZsm7QnOFcgzslYu8GluGwTQI\n"
				+ "rC/+5UO5nnDUsjV1joxLpoQG58u4nsUWbkK2UGlAvCnnnVcE+R6PW336GMnDK63n\n"
				+ "ZODI0SjyKTYnA9fTpUBnMrqG4M2Sl0zdSC3+g8c6n+RVUyka2qJNG+e/9Zs1A3Sx\n"
				+ "rRFBgesLVeqbRHyq38eyHiRiKzhggh8/3kMuZvDRVxXYr3MgLWSxkAsYZzel8rLP\n"
				+ "AysrSAx/YkK2EepmReRkAO0OAnpzUZjnsYqcfvb1cwK1Qn3pVsi3uQUO70Xsj2b9\n"
				+ "f3TT3vECgYEA6834Lg0/MaZ6VuHxHUf18RnHH0niP0Qd7Jv69xk4pw4fzSi7/Kax\n"
				+ "osNWDAfQK2OodbiiXS4C8RXV1BoikMyfA3EHaZdycW7R64s2cy7NH1cgYdUgsKUU\n"
				+ "LlJozu+a4+XsKkJSMbAGs9KesuXhuI3aoDVkiV842SfiNlnOhOSkIU0CgYEAyR9O\n"
				+ "jzyIBi5wIUdFiow7PGpyFczmtxw5slJwCkn7q/KeimwQSkBbNqsiDDgiFLXy02G1\n"
				+ "WIbjkZ4ZpokwzRuPKZTpMfE4jnrYhYrnl65Oh1rDYOTedZzkrwpVuQrDYWfwwVB1\n"
				+ "lKBVYqaFpWiqBH5RzvTBxTOl6CEqvKJ8ucmKZRUCgYBy+awAMF9Wwzi1sldRkqvd\n"
				+ "0lbf77OMa5c3rlpxL3ALLHzfGPSIXXMRzLLhDNI4xc/3Dn7EdlHi4WWwlMmz1sHV\n"
				+ "+L/HgZvAqWtbsTZsdS55EI9uhG/7EZLEf1QxSm04n/xGIq5XIeAywAi3bBViWnB2\n"
				+ "W3JY9QAQCj1niOSEscNBpQKBgQCHPZMRBE4fgfiKmCdt+9AVVyqx+B+oP8WYYJDQ\n"
				+ "imE+hZ4QTIxDsbTkNP1nJBPew16uv4Q3LDU4hQyQjK4RLhf9iRGqMJ+sR3Tzj60d\n"
				+ "tOs/X0wECsKAaejov1VXYaJyDHe7NkRqlcn01S8HRDMXZG3UcYce+RT129+mCsk+\n"
				+ "EQUL5QKBgQCkc1iNqC072yFna9HDLA958EBUHyVuJs5sVYFChtJoWxwXbta5hMBe\n"
				+ "6g/7Pp3pHJueOT4KgRbvmUHy2m2RT3vI697SjUNgI6dGsLNp06RMfsua3+W+T2VH\n"
				+ "RxyaC4mnmffdhgRY9K6d9WOGzR0TMKQng32vW6favItZvpD18Hl+1Q==\n";


		String key = orderResponse.get("key").toString();
		String data = orderResponse.get("data").toString();
		try {
			byte[] decryptedKeys = rsaDecryption(key, privateKey);
			System.out.println(new String(decryptedKeys));
			String decodedData = decode(data);
			String keys = new String(decryptedKeys);
			// String tt = Base64.getEncoder().encodeToString(decodedData);
			String decryptedData = decryptStringToAES256Text( decodedData, keys);
			JSONParser parser = new JSONParser();
			return (JSONObject) parser.parse(decryptedData);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

	public static JSONObject createBMTokenRequest(String data, String key, String ivStr) throws Exception {
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5hw4ZF40bYqdC23yiibE\n"
				+ "60TPOf4x3tLogozSulGQQCPH2jfNDHUNuiLNh1vNtW0HoVwaddLYQvAY18VjWa4g\n"
				+ "HETC/5yEBMPKV7Ku/5/Eylt9l/g+aS8HDjgleZ1MnyKsFPKfJhOHgQzRIEitb8xu\n"
				+ "FyI8099dw+nVT310/dM3d+zTj4ZpUi5J73hMCV0WFQ5UGfPDJuGKSHAm3qGbCQDP\n"
				+ "DsVeIQb1JCqIIuWy7Ef8HfEK7e9+RD0tuV2Lmpj98eAMlzNiUdj5M4QN82VY+kH0\n"
				+ "sInecQY1RmdCRBsdppbd76Wn4TsL3tjY0FDbRhGOsQYn/hD8SfVRqaF4QIj9P+Ic\n" + "DwIDAQAB\n";
		String encryptedData = "";
		try {
			encryptedData = encryptStringToAES256Text(data, key, ivStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("value", encryptedData);
		orderRequest.put("mac", createHash(ivStr + encryptedData, Base64.getEncoder().encodeToString(key.getBytes())));
		String result = orderRequest.toJSONString();

        // String decryptedData = decryptStringToAES256Text(result, key);
        // System.out.println(encryptedData +" <= encrypted data "+decryptedData+" <=decrypted data");
		String encryptedKey = "";
		try {
			encryptedKey = rsaEncryption(key, ivStr, publicKey);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		JSONObject request = new JSONObject();
		request.put("data", Base64.getEncoder().encodeToString(result.getBytes()));
		request.put("key", encryptedKey);
		return request;
	}

	public static String rsaEncryption(String data, String iv, String publicKey) throws BadPaddingException,
			IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

		Cipher cipher = null;
		JSONObject keysRequest = new JSONObject();
		keysRequest.put("AESKey", data);
		keysRequest.put("iv", iv);
		String keyString = keysRequest.toJSONString();
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		} catch (InvalidKeyException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception occured while trying to encrypt: " + e);
		}
		return Base64.getEncoder().encodeToString(cipher.doFinal(keyString.getBytes()));
	}

	public static PublicKey getPublicKey(String base64PublicKey) {
		PublicKey publicKey = null;
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
					java.util.Base64.getMimeDecoder().decode(base64PublicKey.getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("" + e);
		} catch (InvalidKeySpecException e) {
			System.out.println("" + e);
		}
		return publicKey;
	}

	public static String createHash(String message, String secret) {
		String hash = "";
		try {
			byte[] secretByteArray = Base64.getDecoder().decode(secret);
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secretByteArray, "HmacSHA256");
			sha256_HMAC.init(secret_key);
			hash = byteArrayToHex(sha256_HMAC.doFinal(message.getBytes()));
			System.out.println(hash);
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return hash;
	}

	public static String byteArrayToHex(byte[] a) {
		StringBuilder sb = new StringBuilder(a.length * 2);
		for (byte b : a)
			sb.append(String.format("%02x", b));
		return sb.toString();
	}

	public static String encryptStringToAES256Text(String value, String key, String ivStr) throws Exception {
		try {
            byte[] ivBytes = Base64.getDecoder().decode(ivStr);
            byte[] keyBytes = Base64.getDecoder().decode(key);
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			Key skeySpec = generateKey(key);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			String result =  Base64.getEncoder().encodeToString(encrypted);
            System.out.println(result+"result");
            return result;
		} catch (Exception e) {
			System.out.println("Something went wrong!! Please provide valid input.");
			throw e;
		}
	}

    public static String decryptStringToAES256Text(String data, String Keys) throws Exception {
		try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(data);
            JSONObject keyJson = (JSONObject) parser.parse(Keys);
            String ivText = keyJson.get("iv").toString();
            String Key = keyJson.get("AESKey").toString();
            String dataText = json.get("value").toString();
            System.out.println(ivText+"<<<<<<<<>>>>>>"+Key+"<<<<<<<>>>>>>"+ dataText);
            byte[] ivBytes = Base64.getDecoder().decode(ivText);
            byte[] keyBytes = Base64.getDecoder().decode(Key);
            byte[] responseData = Base64.getDecoder().decode(dataText);  //.decode(dataText);
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(responseData);
			return new String(encrypted);
            // return null;
		} catch (Exception e) {
			System.out.println("Something went wrong!! Please provide valid input.");
            return null;
			// throw e;
		}
	}

	public static byte[] rsaDecryption(String data, String base64PrivateKey) throws IllegalBlockSizeException,
			InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return decrypt(java.util.Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
	}

	public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		System.out.println(data.length);
		return cipher.doFinal(data);
	}

	public static PrivateKey getPrivateKey(String base64PrivateKey) {
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PrivateKey privateKey = null;
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
				java.util.Base64.getMimeDecoder().decode(base64PrivateKey.getBytes()));
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			privateKey = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

    private static Key generateKey(String secret) throws Exception {
        byte[] b = secret.getBytes();
        byte[] decoded = Base64.getDecoder().decode(b);
        Key key = new SecretKeySpec(decoded, "AES");
        return key;
    }

    public static String decode(String str) {
        byte[] decoded = Base64.getDecoder().decode(str.getBytes());
        return new String(decoded);
    }

    public static String encode(String str) {
        byte[] encoded = Base64.getEncoder().encode(str.getBytes());
        return new String(encoded);
    }

	public static String randomAlphanumericString(int length) {
		char startChar = 33;
		char endChar = 126;
	
		StringBuffer randomString = new StringBuffer(length);
		Random random = new Random();
	
		for (int i = 0; i < length; i++) {
			int randomIndex = startChar + random.nextInt(endChar - startChar + 1);
			randomString.append((char) randomIndex);
		}
	
		return randomString.toString();
	}
}