package example.company.jse.fiddle;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.pkcs12.PKCS12Builder;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.OIDS;

public class Fiddle21 {

	/* Retrieve the certificate */

	@Test
	public void fiddle() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException,
			InvalidParameterSpecException, NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, IOException, CertificateException, KeyStoreException,
			UnrecoverableKeyException {

		byte[] actualBytes = getActualBytes();

		byte[] expectedBytes = getExpectedBytes();

		Assert.assertArrayEquals(expectedBytes, actualBytes);

	}

	public byte[] getActualBytes()
			throws InvalidKeySpecException, NoSuchAlgorithmException, IOException, InvalidParameterSpecException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, UnrecoverableKeyException, CertificateException, KeyStoreException {

		PKCS12Builder builder = FiddleCommon.getSamplePKCS12Builder();

		String password = "password";
		byte[] input = builder.getEncryptedCertificateBytes();
		byte[] parameters = builder.getCertificateCipherParameters().encode();

		byte[] output = decrypt(password, input, parameters);

		return AsnUtils.parse(output).asSequence().getSequence(0).getContextSpecific(1).parse().asSequence()
				.getContextSpecific(1).parse().asOctetString().getValue();
	}

	private byte[] decrypt(String password, byte[] input, byte[] parameters) throws InvalidKeySpecException,
			NoSuchAlgorithmException, IOException, InvalidParameterSpecException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		SecretKey key = SecretKeyFactory.getInstance("PBE").generateSecret(new PBEKeySpec(password.toCharArray()));

		AlgorithmParameters ap = AlgorithmParameters.getInstance("PBE");
		ap.init(parameters);
		PBEParameterSpec pbeps = ap.getParameterSpec(PBEParameterSpec.class);
		pbeps.getIterationCount();

		Cipher cipher = Cipher.getInstance(OIDS.PBE_WITH_SHA_AND_40_BIT_RC2_CBC);
		cipher.init(Cipher.DECRYPT_MODE, key, pbeps);
		return cipher.doFinal(input);
	}

	public byte[] getExpectedBytes()
			throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {

		KeyStore ks = FiddleCommon.getKeyStore(FiddleCommon.CLIENT_JKS_7, "password");
		X509Certificate x = FiddleCommon.getCertificate(ks, "clientCA");
		return x.getEncoded();

	}

}
