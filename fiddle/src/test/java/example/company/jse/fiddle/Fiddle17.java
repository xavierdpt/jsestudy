package example.company.jse.fiddle;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;

public class Fiddle17 {

	/**
	 * Inspect clinetca.crt : it is exactly the same as in the keystore
	 * 
	 **/

	@Test
	public void fiddle() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException {

		KeyStore keyStore = FiddleCommon.getKeyStore(FiddleCommon.CLIENT_JKS_5, "password");
		X509Certificate cax = FiddleCommon.getCertificate(keyStore, "clientCA");
		Assert.assertNotNull(cax);

		byte[] bytes = FiddleCommon.getCertificateBytes(FiddleCommon.CLIENTCA_CRT_5);

		Assert.assertArrayEquals(cax.getEncoded(), bytes);

	}

}
