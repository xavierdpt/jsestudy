package example.company.jse.fiddle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class Fiddle20 {

	/* New stuff */

	@Test
	public void fiddle() throws NoSuchAlgorithmException, IOException, InvalidParameterSpecException {

		PKCS12Builder builder = new PKCS12Builder();

	}

	@SuppressWarnings("unused")
	private void load() throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
		KeyStore ks2 = FiddleCommon.getKeyStore(FiddleCommon.CLIENTCA_P12_7, "password");

		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		IOUtils.copy(this.getClass().getResourceAsStream(FiddleCommon.CLIENTCA_P12_7), baos2);

		byte[] bytes2 = baos2.toByteArray();

		PKCS12Builder builder = new PKCS12Builder();

		Assert.assertArrayEquals(bytes2, builder.encode());
	}

	@SuppressWarnings("unused")
	private void parts() throws NoSuchAlgorithmException, IOException, InvalidParameterSpecException,
			InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {

		byte[] apBytes = new byte[] {};

		AlgorithmParameters ap = AlgorithmParameters.getInstance("PBE");
		ap.init(apBytes);
		PBEParameterSpec pbeps = ap.getParameterSpec(PBEParameterSpec.class);
		int ic = pbeps.getIterationCount();

		char[] floupy = new char[] {};
		PBEKeySpec keySpec = new PBEKeySpec(floupy);

		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBE");
		SecretKey secret = skf.generateSecret(keySpec);

		Cipher cipher = Cipher.getInstance("1.2.840.113549.1.12.1.6");
		cipher.init(Cipher.DECRYPT_MODE, secret, pbeps);
		byte[] toto = new byte[] {};
		byte[] hop = cipher.doFinal(toto);

		Mac mac = Mac.getInstance("HmacPBESHA1");
		Key k = null;
		AlgorithmParameterSpec p = null;
		mac.init(k, p);
		byte[] bytes = null;
		mac.update(bytes);
		byte[] result = mac.doFinal();
	}
}
