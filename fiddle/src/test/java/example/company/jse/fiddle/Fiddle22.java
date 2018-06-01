package example.company.jse.fiddle;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.pkcs12.PKCS12Builder;
import example.company.asn.utils.OIDS;

public class Fiddle22 {

	/* Retrieve the private key */

	@Test
	public void fiddle()
			throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, InvalidParameterSpecException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, CertificateException, KeyStoreException, UnrecoverableKeyException {

		PKCS12Builder builder = FiddleCommon.getSamplePKCS12Builder();

		byte[] expectedBytes = getExpected();

		Assert.assertArrayEquals(expectedBytes, getActualBytes(builder));

	}

	private byte[] getActualBytes(PKCS12Builder b) throws InvalidKeySpecException, NoSuchAlgorithmException,
			IOException, InvalidParameterSpecException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		String password = "password";
		byte[] input = b.getEncryptedPrivateKeyBytes();
		byte[] parameters = b.getPrivateKeyCipherParameters().encode();

		return decrypt(password, input, parameters);

	}

	private byte[] decrypt(String password, byte[] input, byte[] parameters)
			throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKey key = SecretKeyFactory.getInstance("PBE").generateSecret(new PBEKeySpec(password.toCharArray()));

		AlgorithmParameters ap = AlgorithmParameters.getInstance("PBE");
		ap.init(parameters);

		Cipher cipher = Cipher.getInstance(OIDS.PBE_WITH_SHA_AND_3_KEY_TRIPLE_DES_CBC);
		cipher.init(Cipher.DECRYPT_MODE, key, ap);
		return cipher.doFinal(input);
	}

	private byte[] getExpected() throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException,
			UnrecoverableKeyException {
		KeyStore keyStore = FiddleCommon.getKeyStore(FiddleCommon.CLIENT_JKS_7, "password");
		PrivateKey privateKey = FiddleCommon.getPrivateKey(keyStore, "clientCA", "password");
		return privateKey.getEncoded();
	}

}
