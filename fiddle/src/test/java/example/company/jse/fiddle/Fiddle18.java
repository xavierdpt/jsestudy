package example.company.jse.fiddle;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;

import xdptdr.asn.pem.PEMUtils;

public class Fiddle18 {

	/** Check that client certificate in keystore was replaced with signed certificate **/
	
	@Test
	public void fiddle() throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException,
			UnrecoverableKeyException {

		KeyStore ks5 = FiddleCommon.getKeyStore(FiddleCommon.CLIENT_JKS_5, "password");

		KeyStore ks6 = FiddleCommon.getKeyStore(FiddleCommon.CLIENT_JKS_6, "password");

		X509Certificate cax5 = FiddleCommon.getCertificate(ks5, "clientCA");
		X509Certificate cx5 = FiddleCommon.getCertificate(ks5, "client");
		PrivateKey cpk5 = FiddleCommon.getPrivateKey(ks5, "client", "password");
		PrivateKey capk5 = FiddleCommon.getPrivateKey(ks5, "clientCA", "password");

		X509Certificate cax6 = FiddleCommon.getCertificate(ks6, "clientCA");
		X509Certificate cx6 = FiddleCommon.getCertificate(ks6, "client");
		PrivateKey cpk6 = FiddleCommon.getPrivateKey(ks6, "client", "password");
		PrivateKey capk6 = FiddleCommon.getPrivateKey(ks6, "clientCA", "password");

		byte[] crt = PEMUtils.getCertificateBytes(FiddleCommon.getInputStream(FiddleCommon.CLIENT_CRT_6));

		// Private keys did not change
		Assert.assertArrayEquals(cpk5.getEncoded(), cpk6.getEncoded());
		Assert.assertArrayEquals(capk5.getEncoded(), capk6.getEncoded());

		// CA certificate in keystore did not change
		Assert.assertArrayEquals(cax5.getEncoded(), cax6.getEncoded());

		// Client certificate in keystore DID change
		Assert.assertThat(cx5.getEncoded(), not(equalTo(cx6.getEncoded())));

		// It was replaced with the content of client.crt
		Assert.assertArrayEquals(cx6.getEncoded(), crt);

	}
}
