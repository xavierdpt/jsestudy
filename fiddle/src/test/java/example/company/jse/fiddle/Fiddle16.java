package example.company.jse.fiddle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.junit.Test;

public class Fiddle16 {

	/** Verify client.crt with ca public key automatically and manually **/

	@Test
	public void fiddle() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException,
			InvalidKeyException, NoSuchProviderException, SignatureException {

		KeyStore keyStore = FiddleCommon.getKeyStore(FiddleCommon.CLIENT_JKS_4, "password");

		X509Certificate caCertificate = FiddleCommon.getCertificate(keyStore, "clientCA");

		byte[] expectedBytes = FiddleCommon.getCertificateBytes(FiddleCommon.CLIENT_CRT_4);

		X509Certificate x = (X509Certificate) CertificateFactory.getInstance("X.509")
				.generateCertificate(new ByteArrayInputStream(expectedBytes));

		PublicKey caPublicKey = caCertificate.getPublicKey();

		x.verify(caPublicKey);

		Signature s = Signature.getInstance(x.getSigAlgName());
		s.initVerify(caPublicKey);
		s.update(x.getTBSCertificate());
		s.verify(x.getSignature());

	}
}
