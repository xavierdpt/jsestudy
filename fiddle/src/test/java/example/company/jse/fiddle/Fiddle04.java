package example.company.jse.fiddle;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Test;

public class Fiddle04 {

	/**
	 * "Manually" verify the signature of the certificate with the public key
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
			InvalidKeyException, SignatureException {

		char[] password = "password".toCharArray();

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/keystore1.jks");
		keystore.load(resourceAsStream, password);

		X509Certificate certificate = (X509Certificate) keystore.getCertificate("selfsigned");
		PublicKey publicKey = certificate.getPublicKey();

		Signature sig = Signature.getInstance("SHA256withRSA");

		sig.initVerify(publicKey);
		sig.update(certificate.getTBSCertificate());
		sig.verify(certificate.getSignature());

	}

}
