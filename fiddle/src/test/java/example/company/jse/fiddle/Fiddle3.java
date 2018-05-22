package example.company.jse.fiddle;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;

public class Fiddle3 {

	/**
	 * "Manually" computes the signature of the self-signed certificate in keystore1 with the private key
	 * 
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableKeyException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	@Test
	public void fiddle() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException, InvalidKeyException, SignatureException {

		char[] password = "password".toCharArray();

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/keystore1.jks");
		keystore.load(resourceAsStream, password);

		X509Certificate certificate = (X509Certificate) keystore.getCertificate("selfsigned");
		PrivateKey privateKey = (PrivateKey) keystore.getKey("selfsigned", password);

		Signature sig = Signature.getInstance("SHA256withRSA");

		sig.initSign(privateKey);
		sig.update(certificate.getTBSCertificate());
		byte[] expected = sig.sign();

		Assert.assertArrayEquals(expected, certificate.getSignature());

	}

}
