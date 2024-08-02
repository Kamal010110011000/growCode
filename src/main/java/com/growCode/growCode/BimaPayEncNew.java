package com.growCode.growCode;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jcifs.util.Base64;

public class BimaPayEncNew {
	static String privateKey = "MIIEpAIBAAKCAQEAuUGOKednc62eUsbDDuQeQe0Pxa1yYl2YJaDzvfk0oporMAlg"+
		"OT5FxO76EsPgqo+cQjTCwSkqMg2CcAxaBaS/mCfiSE9Wp0cUdv1zXdeTkU2HQXQV"+
		"YZkDC/n0nTDgkVbsT1lpET2NqBiPl3fAVTvPAMB1zo6I0L85HJAOhWs9lLvsH39b"+
		"OYlcjM4LuybenNl9Sl5Rn/PrlhnQ3l/eHt8giRMuOMn2SEtI2wC83XiPoTEwt0IC"+
		"3zk+kdruE2kb4lE8gTMbT2rvuexYjpR9ww4UzxjFV7dgmAx8aS0IDp+0RSk3WoaQ"+
		"3K0fuWAhmjL1nOzLJUCduvwajUvod9pf6xwcUQIDAQABAoIBAGCQdDsRfNJGcJ1z"+
		"L5+mdsMkf7EprNLrOHB8yjB/ItmTBOoAKSENfa6eZsm7QnOFcgzslYu8GluGwTQI"+
		"rC/+5UO5nnDUsjV1joxLpoQG58u4nsUWbkK2UGlAvCnnnVcE+R6PW336GMnDK63n"+
		"ZODI0SjyKTYnA9fTpUBnMrqG4M2Sl0zdSC3+g8c6n+RVUyka2qJNG+e/9Zs1A3Sx"+
		"rRFBgesLVeqbRHyq38eyHiRiKzhggh8/3kMuZvDRVxXYr3MgLWSxkAsYZzel8rLP"+
		"AysrSAx/YkK2EepmReRkAO0OAnpzUZjnsYqcfvb1cwK1Qn3pVsi3uQUO70Xsj2b9"+
		"f3TT3vECgYEA6834Lg0/MaZ6VuHxHUf18RnHH0niP0Qd7Jv69xk4pw4fzSi7/Kax"+
		"osNWDAfQK2OodbiiXS4C8RXV1BoikMyfA3EHaZdycW7R64s2cy7NH1cgYdUgsKUU"+
		"LlJozu+a4+XsKkJSMbAGs9KesuXhuI3aoDVkiV842SfiNlnOhOSkIU0CgYEAyR9O"+
		"jzyIBi5wIUdFiow7PGpyFczmtxw5slJwCkn7q/KeimwQSkBbNqsiDDgiFLXy02G1"+
		"WIbjkZ4ZpokwzRuPKZTpMfE4jnrYhYrnl65Oh1rDYOTedZzkrwpVuQrDYWfwwVB1"+
		"lKBVYqaFpWiqBH5RzvTBxTOl6CEqvKJ8ucmKZRUCgYBy+awAMF9Wwzi1sldRkqvd"+
		"0lbf77OMa5c3rlpxL3ALLHzfGPSIXXMRzLLhDNI4xc/3Dn7EdlHi4WWwlMmz1sHV"+
		"+L/HgZvAqWtbsTZsdS55EI9uhG/7EZLEf1QxSm04n/xGIq5XIeAywAi3bBViWnB2"+
		"W3JY9QAQCj1niOSEscNBpQKBgQCHPZMRBE4fgfiKmCdt+9AVVyqx+B+oP8WYYJDQ"+
		"imE+hZ4QTIxDsbTkNP1nJBPew16uv4Q3LDU4hQyQjK4RLhf9iRGqMJ+sR3Tzj60d"+
		"tOs/X0wECsKAaejov1VXYaJyDHe7NkRqlcn01S8HRDMXZG3UcYce+RT129+mCsk+"+
		"EQUL5QKBgQCkc1iNqC072yFna9HDLA958EBUHyVuJs5sVYFChtJoWxwXbta5hMBe"+
		"6g/7Pp3pHJueOT4KgRbvmUHy2m2RT3vI697SjUNgI6dGsLNp06RMfsua3+W+T2VH"+
		"RxyaC4mnmffdhgRY9K6d9WOGzR0TMKQng32vW6favItZvpD18Hl+1Q==";
	static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5hw4ZF40bYqdC23yiibE\n"+
			"60TPOf4x3tLogozSulGQQCPH2jfNDHUNuiLNh1vNtW0HoVwaddLYQvAY18VjWa4g\n"+
			"HETC/5yEBMPKV7Ku/5/Eylt9l/g+aS8HDjgleZ1MnyKsFPKfJhOHgQzRIEitb8xu\n"+
			"FyI8099dw+nVT310/dM3d+zTj4ZpUi5J73hMCV0WFQ5UGfPDJuGKSHAm3qGbCQDP\n"+
			"DsVeIQb1JCqIIuWy7Ef8HfEK7e9+RD0tuV2Lmpj98eAMlzNiUdj5M4QN82VY+kH0\n"+
			"sInecQY1RmdCRBsdppbd76Wn4TsL3tjY0FDbRhGOsQYn/hD8SfVRqaF4QIj9P+Ic\n"+
			"DwIDAQAB\n";

// 	static String privateKey = "MIIEpAIBAAKCAQEAuUGOKednc62eUsbDDuQeQe0Pxa1yYl2YJaDzvfk0oporMAlg\n"+
// "OT5FxO76EsPgqo+cQjTCwSkqMg2CcAxaBaS/mCfiSE9Wp0cUdv1zXdeTkU2HQXQV\n"+
// "YZkDC/n0nTDgkVbsT1lpET2NqBiPl3fAVTvPAMB1zo6I0L85HJAOhWs9lLvsH39b\n"+
// "OYlcjM4LuybenNl9Sl5Rn/PrlhnQ3l/eHt8giRMuOMn2SEtI2wC83XiPoTEwt0IC\n"+
// "3zk+kdruE2kb4lE8gTMbT2rvuexYjpR9ww4UzxjFV7dgmAx8aS0IDp+0RSk3WoaQ\n"+
// "3K0fuWAhmjL1nOzLJUCduvwajUvod9pf6xwcUQIDAQABAoIBAGCQdDsRfNJGcJ1z\n"+
// "L5+mdsMkf7EprNLrOHB8yjB/ItmTBOoAKSENfa6eZsm7QnOFcgzslYu8GluGwTQI\n"+
// "rC/+5UO5nnDUsjV1joxLpoQG58u4nsUWbkK2UGlAvCnnnVcE+R6PW336GMnDK63n\n"+
// "ZODI0SjyKTYnA9fTpUBnMrqG4M2Sl0zdSC3+g8c6n+RVUyka2qJNG+e/9Zs1A3Sx\n"+
// "rRFBgesLVeqbRHyq38eyHiRiKzhggh8/3kMuZvDRVxXYr3MgLWSxkAsYZzel8rLP\n"+
// "AysrSAx/YkK2EepmReRkAO0OAnpzUZjnsYqcfvb1cwK1Qn3pVsi3uQUO70Xsj2b9\n"+
// "f3TT3vECgYEA6834Lg0/MaZ6VuHxHUf18RnHH0niP0Qd7Jv69xk4pw4fzSi7/Kax\n"+
// "osNWDAfQK2OodbiiXS4C8RXV1BoikMyfA3EHaZdycW7R64s2cy7NH1cgYdUgsKUU\n"+
// "LlJozu+a4+XsKkJSMbAGs9KesuXhuI3aoDVkiV842SfiNlnOhOSkIU0CgYEAyR9O\n"+
// "jzyIBi5wIUdFiow7PGpyFczmtxw5slJwCkn7q/KeimwQSkBbNqsiDDgiFLXy02G1\n"+
// "WIbjkZ4ZpokwzRuPKZTpMfE4jnrYhYrnl65Oh1rDYOTedZzkrwpVuQrDYWfwwVB1\n"+
// "lKBVYqaFpWiqBH5RzvTBxTOl6CEqvKJ8ucmKZRUCgYBy+awAMF9Wwzi1sldRkqvd\n"+
// "0lbf77OMa5c3rlpxL3ALLHzfGPSIXXMRzLLhDNI4xc/3Dn7EdlHi4WWwlMmz1sHV\n"+
// "+L/HgZvAqWtbsTZsdS55EI9uhG/7EZLEf1QxSm04n/xGIq5XIeAywAi3bBViWnB2\n"+
// "W3JY9QAQCj1niOSEscNBpQKBgQCHPZMRBE4fgfiKmCdt+9AVVyqx+B+oP8WYYJDQ\n"+
// "imE+hZ4QTIxDsbTkNP1nJBPew16uv4Q3LDU4hQyQjK4RLhf9iRGqMJ+sR3Tzj60d\n"+
// "tOs/X0wECsKAaejov1VXYaJyDHe7NkRqlcn01S8HRDMXZG3UcYce+RT129+mCsk+\n"+
// "EQUL5QKBgQCkc1iNqC072yFna9HDLA958EBUHyVuJs5sVYFChtJoWxwXbta5hMBe\n"+
// "6g/7Pp3pHJueOT4KgRbvmUHy2m2RT3vI697SjUNgI6dGsLNp06RMfsua3+W+T2VH\n"+
// "RxyaC4mnmffdhgRY9K6d9WOGzR0TMKQng32vW6favItZvpD18Hl+1Q==\n";
	// static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmHsHmBAFIiO7KDZKu5i5OyDMxWzHYtXAbVGtrMojeE0b8X6Ay8/8RmL4VAWD5YPAo/WmS0AlL6Ru61a3/vGrLd3pzOg+5n8N2T6dxDnBev74qECZwtJspB2BI68m7c2MzWDnmzRA5ujm5Kv0w0c4maW37qGkR7zvIKtqggBz+ZQIDAQAB";
	// static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKYeweYEAUiI7soNkq7mLk7IMzFbMdi1cBtUa2syiN4TRvxfoDLz/xGYvhUBYPlg8Cj9aZLQCUvpG7rVrf+8ast3enM6D7mfw3ZPp3EOcF6/vioQJnC0mykHYEjrybtzYzNYOebNEDm6Obkq/TDRziZpbfuoaRHvO8gq2qCAHP5lAgMBAAECgYBQa410/DVe1yO6321GjJqYeyUFXN+5yMR4BptmLvXBhNWVsfmNEUBHVYmYA63drIc3YM7uSxZlEHnjLjTpHF8aTxsqtN2jIPoWT7gXI2+voWrako0oaTUsG/ZbHPaq5R+llp3i9fn4i0GnhLwcgG/Q9iHs/ijbTs6whIqVFSvewQJBAN0S9Ibs+IRwhHgpPXhGo4pGxKuOJWR1wMCXt0KRuOH4Mqi3Mi3DZE3bFqTo7OzgSXhRXhD7pNpVWuuPLMjnohUCQQDAXUPgrnt//0S+GNuqDdpYfUMfD1FizfyrXagCVTI3WWgogcwUWOR0J4z8IRWt+lFwrEWsOulR+zF80Z2YTA8RAkBurwrWctaKGHt6Xct06IkAtiXJbsWx4nK6+jq6jZLvMADND8uDtgwGHubbcfMNNc4S+0oJXrROy+VVgr19aNtxAkBR4WEbKin3ebjREdOutubR87+2YNbLr9J1PxBcrdcKKExlsZokrxHPP2aLi6mmvH4d77ZYcVsPQlT2RGNIiFeBAkEAyOxgXW1/6VzX1dLIfyGu6ELcSy1bA7U4t+JBW+W2PscGl2m+CiTBhUy6tPzjiuMUvH2Zu5S7gTaBTgR/uZfoQw==";
	public static void main(String args[]) {

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("email", "partner.care@bimapay.in");
		orderRequest.put("password", "password");
		orderRequest.put("userType", "partner");
		System.out.println("create token request payload: " + orderRequest.toString());

		String data = null;
		try {
			data = new ObjectMapper().writeValueAsString(orderRequest);
		} catch (JsonProcessingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		byte[] iVarray = new byte[16];
		new Random().nextBytes(iVarray);
		// need 16 byte iv but with new String its length gets reduced
		System.out.println(new String(iVarray)+"<<<<<<<>>>>>"+new String(iVarray).length());
		// String ivStr = iVarray.toString();


		byte[] KeyArray = new byte[32];
		new Random().nextBytes(KeyArray);

		// need 32 byte key but with new String its length gets reduced
		System.out.println(new String(KeyArray)+"<<<<<<<>>>>>"+new String(KeyArray).length());
		// String key = byteArrayToHex(array2);

		// System.out.println("key and iv : " + ivStr + "\n"+ key);

		orderRequest = createBMTokenRequest(data, KeyArray, iVarray);
		System.out.println("request after createBMTokenRequest : " + orderRequest + "\n");
		System.out.println("request after createBMTokenRequest : " + byteArrayToHex(KeyArray) + "\n");

        // String keyEnc= "422NAfHnkC7MB9mRmwuZDf8Txzacz4BWUgozzc0CtQ/LEiDVbjp8u4ZYy1+s3haKuq6Cn4YKu+Y3afSBpAcN3K4wrqONZNjWNvxxHYJGuFeUlWOydTLXjAm9vKTw/R/6zue+7MMhqbb/KybuBObX0siePBjlxIbZT+nHdpdoI1LVzMc8y2TcwOvjgNzIc2beg+L257jDLz+Oehh//viqO1lrlsUflKsK3TbpsdE+XTxofaJsdnEHEWj3AhORK0XpcAQVYS5t45SoCHeM/IH2uwS52Mu+6tqtpyXvSo8nG3S81xpV/bzHZuD0NQ2OgUnuCHXoEl2o/r/TM6wH22w9SQ==";

		// byte[] decryptData = null;
		// try {
		// 	decryptData = rsaDecryption((keyEnc), privateKey);
		// 	System.out.println(decryptData);
		// } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
		// 		| NoSuchPaddingException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }
	}

	public static JSONObject createBMTokenRequest(String data, byte[] key, byte[] ivStr) {
			String encryptedData = "";
		try {
			encryptedData = encryptStringToAES256Text(data, key, ivStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("iv", Base64.encode(ivStr));
		orderRequest.put("value", encryptedData);
		orderRequest.put("mac", createHash(Base64.encode(ivStr) + encryptedData, byteArrayToHex(key)));
		String result = orderRequest.toJSONString();

		System.out.println(orderRequest+"\n<<<<<<<<<<<>>>>>>>>"+ Base64.encode(ivStr));
		String encryptedKey = "";
		try {
			System.out.println("\n<<<<<<<<<<<>>>>>>>> key=> "+ byteArrayToHex(key));
			encryptedKey = rsaEncryption(byteArrayToHex(key), publicKey);
			System.out.println("\n<<<<<<<<<<<>>>>>>>> encrypted key => "+ encryptedKey);
			try {
				byte[] decryptedKey = rsaDecryption(encryptedData, privateKey);
				System.out.println("\n<<<<<<<<<<<>>>>>>>> encrypted key => "+Base64.encode(decryptedKey));
			} catch (Exception e) {
				System.err.println(e);
				// TODO: handle exception
			}
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
		request.put("data", Base64.encode(result.getBytes(StandardCharsets.UTF_8)));
		request.put("key", encryptedKey);
		return request;
	}

	public static String rsaEncryption(String data, String publicKey) throws BadPaddingException,
			IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {

		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		} catch (InvalidKeyException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception occured while trying to encrypt: " + e);
		}
		return Base64.encode(cipher.doFinal(data.getBytes()));
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
			byte[] secretByteArray = secret.getBytes(StandardCharsets.UTF_16);
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

	public static String encryptStringToAES256Text(String value, byte[] key, byte[] ivStr) throws Exception {
		try {
			IvParameterSpec iv = new IvParameterSpec(ivStr);
            System.out.println(byteArrayToHex(ivStr)+"<<<<<<<iv");
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.encode(encrypted);
		} catch (Exception e) {
			System.out.println("Something went wrong!! Please provide valid input.");
			throw e;
		}
	}

	public static byte[] rsaDecryption(String data, String base64PrivateKey) throws IllegalBlockSizeException,
			InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return decrypt(Base64.decode(data), getPrivateKey(base64PrivateKey));
	}

	public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		System.out.println(data.length+" <<<>>>>>"+ Base64.encode(data).length());
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

}
