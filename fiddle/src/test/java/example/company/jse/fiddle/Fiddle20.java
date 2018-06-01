package example.company.jse.fiddle;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.junit.Assert;
import org.junit.Test;

public class Fiddle20 {

	/*
	 * Verify MAC of clientca.p12 through PKCS12Builder which generates the same
	 * bytes
	 */

	@Test
	public void fiddle() throws NoSuchAlgorithmException, IOException, InvalidParameterSpecException,
			InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException, UnrecoverableKeyException,
			CertificateException, KeyStoreException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {

		PKCS12Builder builder = FiddleCommon.getSamplePKCS12Builder();

		byte[] salt = builder.getMacSalt();
		int iterationCount = builder.getMacIterationCount();
		String password = "password";
		byte[] content = builder.getContentBytes();
		byte[] hash = builder.getMacHash(content);

		verifyMac(salt, iterationCount, password, content, hash);

	}

	private void verifyMac(byte[] salt, int iterationCount, String password, byte[] content, byte[] hash)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
			InvalidAlgorithmParameterException {

		SecretKey key = SecretKeyFactory.getInstance("PBE").generateSecret(new PBEKeySpec(password.toCharArray()));
		PBEParameterSpec params = new PBEParameterSpec(salt, iterationCount);

		Mac mac = Mac.getInstance("HmacPBESHA1");
		mac.init(key, params);
		mac.update(content);
		byte[] result = mac.doFinal();

		Assert.assertArrayEquals(hash, result);

	}

}
