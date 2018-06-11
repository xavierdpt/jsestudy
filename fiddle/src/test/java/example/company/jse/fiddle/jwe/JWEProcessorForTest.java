package example.company.jse.fiddle.jwe;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
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

import org.junit.Assert;

import example.company.acme.v2.JWSBase64;
import example.company.tox.common.Common;

public class JWEProcessorForTest {

	private Cheat cheat;
	private byte[] al;
	private byte[] encryptedCek;
	private String cekAlgorithm;
	private Cipher cekCipher;
	private Map<String, String> cekEncryptionParams;
	private byte[] cek;
	private BigInteger rsaModulus;
	private KeyFactory rsaKeyFactory;
	private String header64;
	private byte[] aad;
	private String encCrypt;
	private String encMac;
	private byte[] cipherText;
	private byte[] authenticationTag;
	private String text;
	private byte[] iv;

	public String process(InputForTest input) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			InvalidKeySpecException {

		cheat = input.getCheat();

		cekAlgorithm = input.getAlg();
		cekEncryptionParams = input.getKeyEncryptionParams();
		cek = input.getKey();
		al = Common.bytes(0, 0, 0, 0, 0, 0, 1, 152);
		header64 = JWSBase64.encode(input.getHeaderJSON().getBytes());
		aad = header64.getBytes();

		initializeCekCipher();
		encryptCek();
		checkEncryptedCek();

		text = input.getText();

		iv = input.getIv();

		encCrypt = input.getEncCrypt();
		encMac = input.getEncMac();

		if (Header.A128CBC.equals(encCrypt) && Header.HS256.equals(encMac)) {
			cbc();
		} else if (Header.A256GCM.equals(encCrypt)) {
			gcm();
		}

		String encryptedCek64 = JWSBase64.encode(encryptedCek);
		String iv64 = JWSBase64.encode(iv);
		String cipherText64 = JWSBase64.encode(cipherText);
		String authenticationTag64 = JWSBase64.encode(authenticationTag);

		return header64 + "." + encryptedCek64 + "." + iv64 + "." + cipherText64 + "." + authenticationTag64;
	}

	private void gcm() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec keySpec = new SecretKeySpec(cek, "AES");
		GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);

		Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5PADDING");

		cipher.init(Cipher.ENCRYPT_MODE, keySpec, parameterSpec);
		cipher.updateAAD(aad);
		byte[] output = cipher.doFinal(text.getBytes());

		cipherText = Common.part(output, 0, 63);
		authenticationTag = Common.part(output, 63, 63 + 16);
	}

	private void cbc() throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

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

	private void checkEncryptedCek() {
		if (cheat.getExpectedEncryptedCek() != null) {
			Assert.assertArrayEquals(cheat.getExpectedEncryptedCek(), encryptedCek);
		}
	}

	private void initializeCekCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
		switch (cekAlgorithm) {
		case Header.A128KW:
			cekCipher = Cipher.getInstance("AESWrap");
			break;
		case Header.RSA1_5:
			cekCipher = Cipher.getInstance("RSA");
			break;
		case Header.RSAOAEP:
			cekCipher = Cipher.getInstance("RSA/ECB/OAEPPADDING");
			break;
		}
	}

	private void encryptCek() throws InvalidKeyException, IllegalBlockSizeException, NoSuchAlgorithmException,
			InvalidKeySpecException, BadPaddingException {
		switch (cekAlgorithm) {
		case Header.A128KW:
			aes128KeyWrap();
			break;
		case Header.RSA1_5:
		case Header.RSAOAEP:
			rsa();
			break;

		}
	}

	private void rsa() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException,
			IllegalBlockSizeException, BadPaddingException {

		rsaModulus = bi("n");

		rsaKeyFactory = KeyFactory.getInstance("RSA");

		if (cheat.getDecryptEncryptedKey()) {
			rsaDecryptAndCheck();
		} else {
			rsaEncrypt();

		}
	}

	private void rsaEncrypt()
			throws InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

		BigInteger publicExponent = bi("e");

		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(rsaModulus, publicExponent);

		PublicKey publicKey = rsaKeyFactory.generatePublic(keySpec);

		cekCipher.init(Cipher.ENCRYPT_MODE, publicKey);

		encryptedCek = cekCipher.doFinal(cek);
	}

	private void rsaDecryptAndCheck()
			throws InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {

		byte[] expectedEncryptedCek = cheat.getExpectedEncryptedCek();

		BigInteger privateExponent = bi("d");

		RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(rsaModulus, privateExponent);

		PrivateKey privateKey = rsaKeyFactory.generatePrivate(keySpec);

		cekCipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] output = cekCipher.doFinal(expectedEncryptedCek);

		Assert.assertArrayEquals(cek, output);

		encryptedCek = expectedEncryptedCek;
	}

	private void aes128KeyWrap() throws InvalidKeyException, IllegalBlockSizeException {

		byte[] secretKey = JWSBase64.decode(cekEncryptionParams.get("k"));

		SecretKeySpec keySpec = new SecretKeySpec(secretKey, "AES");

		SecretKeySpec cekKeySpec = new SecretKeySpec(cek, "RAW");

		cekCipher.init(Cipher.WRAP_MODE, keySpec);

		encryptedCek = cekCipher.wrap(cekKeySpec);
	}

	private BigInteger bi(String name) {
		return Common.bigInteger(JWSBase64.decode(cekEncryptionParams.get(name)));
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
