package example.company.jse.fiddle.jwe;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import example.company.acme.v2.JWSBase64;
import example.company.tox.common.Common;

public class JWEProcessor {

	private byte[] al;
	private String header64;
	private String encCrypt;
	private String encMac;
	private byte[] cipherText;
	private byte[] authenticationTag;
	private String text;

	public String process(Input input) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			InvalidKeySpecException {

		String cekAlgorithm = input.getAlg();
		Map<String, String> params = input.getKeyEncryptionParams();
		byte[] cek = input.getKey();
		al = Common.bytes(0, 0, 0, 0, 0, 0, 1, 152);
		header64 = JWSBase64.encode(input.getHeaderJSON().getBytes());
		byte[] aad = header64.getBytes();

		byte[] encryptedCek = encryptCek(cek, params, cekAlgorithm);

		text = input.getText();

		byte[] iv = input.getIv();

		encCrypt = input.getEncCrypt();
		encMac = input.getEncMac();

		if (Header.A128CBC.equals(encCrypt) && Header.HS256.equals(encMac)) {
			cbc(iv, cek, aad);
		} else if (Header.A256GCM.equals(encCrypt)) {
			gcm(iv, cek, aad);
		}

		String encryptedCek64 = JWSBase64.encode(encryptedCek);
		String iv64 = JWSBase64.encode(iv);
		String cipherText64 = JWSBase64.encode(cipherText);
		String authenticationTag64 = JWSBase64.encode(authenticationTag);

		return header64 + "." + encryptedCek64 + "." + iv64 + "." + cipherText64 + "." + authenticationTag64;
	}

	private void gcm(byte[] iv, byte[] cek, byte[] aad) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec keySpec = new SecretKeySpec(cek, "AES");
		GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

		Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5PADDING");

		cipher.init(Cipher.ENCRYPT_MODE, keySpec, parameterSpec);
		cipher.updateAAD(aad);
		byte[] output = cipher.doFinal(text.getBytes());

		cipherText = Common.part(output, 0, 63);
		authenticationTag = Common.part(output, 63, 63 + 16);
	}

	private void cbc(byte[] iv, byte[] cek, byte[] aad) throws InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

		byte[] encKey = getEncKey(cek);
		byte[] macKey = getMacKey(cek);

		SecretKeySpec encKeySpec = new SecretKeySpec(encKey, "AES");
		SecretKeySpec macKeySpec = new SecretKeySpec(macKey, "RAW");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

		byte[] input = text.getBytes();

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, encKeySpec, ivParameterSpec);
		cipherText = cipher.doFinal(input);

		byte[] macInput = Common.concatenate(aad, iv, cipherText, al);

		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(macKeySpec);
		byte[] macOutput = mac.doFinal(macInput);

		authenticationTag = getAuthenticationTag(macOutput);

	}

	private byte[] encryptCek(byte[] cek, Map<String, String> params, String cekAlgorithm)
			throws InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException,
			BadPaddingException, NoSuchPaddingException {

		Cipher cipher = null;
		switch (cekAlgorithm) {
		case Header.A128KW:
			cipher = Cipher.getInstance("AESWrap");
			break;
		case Header.RSA1_5:
			cipher = Cipher.getInstance("RSA");
			break;
		case Header.RSAOAEP:
			cipher = Cipher.getInstance("RSA/ECB/OAEPPADDING");
			break;
		}

		switch (cekAlgorithm) {
		case Header.A128KW:
			return aes128KeyWrap(cipher, cek, JWSBase64.decode(params.get("k")));
		case Header.RSA1_5:
		case Header.RSAOAEP:
			return rsa(cipher, cek, params);
		}
		return null;
	}

	private byte[] rsa(Cipher cipher, byte[] cek, Map<String, String> params) throws NoSuchAlgorithmException,
			InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

		BigInteger rsaModulus = bi(params, "n");

		KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");

		BigInteger publicExponent = bi(params, "e");

		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(rsaModulus, publicExponent);

		PublicKey publicKey = rsaKeyFactory.generatePublic(keySpec);

		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(cek);
	}

	private byte[] aes128KeyWrap(Cipher cipher, byte[] cek, byte[] secretKey)
			throws InvalidKeyException, IllegalBlockSizeException {

		SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");

		SecretKeySpec cekKeySpec = new SecretKeySpec(cek, "RAW");

		cipher.init(Cipher.WRAP_MODE, keySpec);

		return cipher.wrap(cekKeySpec);
	}

	private BigInteger bi(Map<String, String> params, String name) {
		return Common.bigInteger(JWSBase64.decode(params.get(name)));
	}

	private static byte[] getEncKey(byte[] key) {
		byte[] encKey = new byte[key.length / 2];
		for (int i = key.length / 2; i < key.length; ++i) {
			encKey[i - key.length / 2] = key[i];
		}
		return encKey;
	}

	private static byte[] getMacKey(byte[] key) {
		byte[] macKey = new byte[key.length / 2];
		for (int i = 0; i < macKey.length; ++i) {
			macKey[i] = key[i];
		}
		return macKey;
	}

	private static byte[] getAuthenticationTag(byte[] hmac) {
		byte[] at = new byte[hmac.length / 2];
		for (int i = 0; i < at.length; ++i) {
			at[i] = hmac[i];
		}
		return at;
	}

}
