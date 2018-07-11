package example.company.jse.fiddle;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.Test;

import xdptdr.common.Common;

public class Fiddle40 {

	/** https://tools.ietf.org/html/rfc7516 Appendix B. */

	@Test
	public void fiddle() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		byte[] macKey = Common.bytes(4, 211, 31, 197, 84, 157, 252, 254, 11, 100, 157, 250, 63, 170, 106, 206);
		byte[] encKey = Common.bytes(107, 124, 212, 45, 111, 107, 9, 219, 200, 177, 0, 240, 143, 156, 44, 207);

		byte[] plainTextBytes = Common.bytes(76, 105, 118, 101, 32, 108, 111, 110, 103, 32, 97, 110, 100, 32, 112, 114,
				111, 115, 112, 101, 114, 46);

		byte[] expectedEncryptOutput = Common.bytes(40, 57, 83, 181, 119, 33, 133, 148, 198, 185, 243, 24, 152, 230, 6,
				75, 129, 223, 127, 19, 210, 82, 183, 230, 168, 33, 215, 104, 143, 112, 56, 102);

		byte[] ivBytes = Common.bytes(3, 22, 60, 12, 43, 67, 104, 105, 108, 108, 105, 99, 111, 116, 104, 101);

		AlgorithmParameterSpec spec = new IvParameterSpec(ivBytes);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encKey, "AES"), spec);
		byte[] actualEncryptOutput = cipher.doFinal(plainTextBytes);

		Assert.assertArrayEquals(expectedEncryptOutput, actualEncryptOutput);

		byte[] aad = Common.bytes(101, 121, 74, 104, 98, 71, 99, 105, 79, 105, 74, 66, 77, 84, 73, 52, 83, 49, 99, 105,
				76, 67, 74, 108, 98, 109, 77, 105, 79, 105, 74, 66, 77, 84, 73, 52, 81, 48, 74, 68, 76, 85, 104, 84, 77,
				106, 85, 50, 73, 110, 48);
		byte[] al = Common.bytes(0, 0, 0, 0, 0, 0, 1, 152);

		byte[] expectedMacInput = Common.bytes(101, 121, 74, 104, 98, 71, 99, 105, 79, 105, 74, 66, 77, 84, 73, 52, 83,
				49, 99, 105, 76, 67, 74, 108, 98, 109, 77, 105, 79, 105, 74, 66, 77, 84, 73, 52, 81, 48, 74, 68, 76, 85,
				104, 84, 77, 106, 85, 50, 73, 110, 48, 3, 22, 60, 12, 43, 67, 104, 105, 108, 108, 105, 99, 111, 116,
				104, 101, 40, 57, 83, 181, 119, 33, 133, 148, 198, 185, 243, 24, 152, 230, 6, 75, 129, 223, 127, 19,
				210, 82, 183, 230, 168, 33, 215, 104, 143, 112, 56, 102, 0, 0, 0, 0, 0, 0, 1, 152);

		byte[] actualMacInput = Common.concatenate(aad, ivBytes, actualEncryptOutput, al);

		Assert.assertArrayEquals(expectedMacInput, actualMacInput);

		Mac m = Mac.getInstance("HmacSHA256");
		m.init(new SecretKeySpec(macKey, "HmacSHA256"));
		byte[] actualMacOutput = m.doFinal(actualMacInput);

		byte[] expectedMacOutput = Common.bytes(83, 73, 191, 98, 104, 205, 211, 128, 201, 189, 199, 133, 32, 38, 194,
				85, 9, 84, 229, 201, 219, 135, 44, 252, 145, 102, 179, 140, 105, 86, 229, 116);

		Assert.assertArrayEquals(expectedMacOutput, actualMacOutput);

		byte[] expectedAuthenticationTag = Common.bytes(83, 73, 191, 98, 104, 205, 211, 128, 201, 189, 199, 133, 32, 38,
				194, 85);

		byte[] actualAuthenticationTag = Common.first(actualMacOutput, 128 / 8);

		Assert.assertArrayEquals(expectedAuthenticationTag, actualAuthenticationTag);
	}

	@Test
	public void fiddle2() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		byte[] macKey = Common.bytes(4, 211, 31, 197, 84, 157, 252, 254, 11, 100, 157, 250, 63, 170, 106, 206);
		byte[] encKey = Common.bytes(107, 124, 212, 45, 111, 107, 9, 219, 200, 177, 0, 240, 143, 156, 44, 207);

		byte[] plainText = Common.bytes(76, 105, 118, 101, 32, 108, 111, 110, 103, 32, 97, 110, 100, 32, 112, 114, 111,
				115, 112, 101, 114, 46);

		byte[] iv = Common.bytes(3, 22, 60, 12, 43, 67, 104, 105, 108, 108, 105, 99, 111, 116, 104, 101);

		byte[] aad = Common.bytes(101, 121, 74, 104, 98, 71, 99, 105, 79, 105, 74, 66, 77, 84, 73, 52, 83, 49, 99, 105,
				76, 67, 74, 108, 98, 109, 77, 105, 79, 105, 74, 66, 77, 84, 73, 52, 81, 48, 74, 68, 76, 85, 104, 84, 77,
				106, 85, 50, 73, 110, 48);

		byte[] al = Common.bytes(0, 0, 0, 0, 0, 0, 1, 152);

		byte[] expectedMacOutput = Common.bytes(83, 73, 191, 98, 104, 205, 211, 128, 201, 189, 199, 133, 32, 38, 194,
				85, 9, 84, 229, 201, 219, 135, 44, 252, 145, 102, 179, 140, 105, 86, 229, 116);

		Assert.assertArrayEquals(expectedMacOutput, frobnicate(plainText, encKey, macKey, iv, aad, al));
	}

	public byte[] frobnicate(byte[] plainText, byte[] encKey, byte[] macKey, byte[] iv, byte[] aad, byte[] al)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encKey, "AES"), new IvParameterSpec(iv));
		byte[] cipherOutput = cipher.doFinal(plainText);

		byte[] macInput = Common.concatenate(aad, iv, cipherOutput, al);

		Mac m = Mac.getInstance("HmacSHA256");
		m.init(new SecretKeySpec(macKey, "HmacSHA256"));
		byte[] actualMacOutput = m.doFinal(macInput);

		return actualMacOutput;
	}
}
