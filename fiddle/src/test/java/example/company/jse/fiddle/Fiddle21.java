package example.company.jse.fiddle;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
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

import org.junit.Assert;
import org.junit.Test;

import example.company.tox.common.Common;

public class Fiddle21 {

	/* Verify MAC of clientca.p12 through PKCS12Builder which generates the same bytes */

	@Test
	public void fiddle() throws NoSuchAlgorithmException, IOException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException {

		PKCS12Builder builder = new PKCS12Builder();
		
		byte[] salt = builder.getMacAsn().asSequence().getOctetString(1).getValue();
		int iterationCount = (int) builder.getMacAsn().asSequence().getInteger(2).getValue();
		
		Mac m = Mac.getInstance("HmacPBESHA1");
		PBEParameterSpec params =
                new PBEParameterSpec(salt, iterationCount);
		SecretKey key = null;
		
		char[] password = "password".toCharArray();
		PBEKeySpec keySpec = new PBEKeySpec(password );
        SecretKeyFactory skFac = SecretKeyFactory.getInstance("PBE");
        key = skFac.generateSecret(keySpec);
        keySpec.clearPassword();
		
		m.init(key, params);
		m.update(builder.getDataContent().getValue());
		byte[] macResult = m.doFinal();
		
		System.out.println(Common.bytesToString(macResult));
		
		Assert.assertArrayEquals(builder.hash, macResult);
	}

	@Test
	public void load() throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
		KeyStore ks2 = FiddleCommon.getKeyStore(FiddleCommon.CLIENTCA_P12_7, "password");
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
