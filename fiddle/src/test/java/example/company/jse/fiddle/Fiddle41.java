package example.company.jse.fiddle;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.Test;

import example.company.tox.common.Common;
import xdptdr.acme.jw.JWBase64;

/** RFC 7516 A.3.3 **/

public class Fiddle41 {

	private String key = "GawgguFyGrWKav7AX4VKUg";
	private String expectedOutput = "6KB707dM9YTIgHtLvtgWQ8mKwboJW3of9locizkDTHzBC2IlrT1oOQ";

	private byte[] input = Common.bytes(4, 211, 31, 197, 84, 157, 252, 254, 11, 100, 157, 250, 63, 170, 106, 206, 107,
			124, 212, 45, 111, 107, 9, 219, 200, 177, 0, 240, 143, 156, 44, 207);

	@Test
	public void fiddle() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Object actualOutput = e(wrap(d(key), input));
		Assert.assertEquals(expectedOutput, actualOutput);

	}

	private byte[] wrap(byte[] key, byte[] input) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException {

		Cipher cipher = Cipher.getInstance("AESWrap");
		cipher.init(Cipher.WRAP_MODE, new SecretKeySpec(key, "AES"), (AlgorithmParameterSpec) null);
		return cipher.wrap(new SecretKeySpec(input, "AES"));

	}

	private byte[] d(String s) {
		return JWBase64.decode(s);
	}

	private Object e(byte[] bytes) {
		return JWBase64.encode(bytes);
	}
}
