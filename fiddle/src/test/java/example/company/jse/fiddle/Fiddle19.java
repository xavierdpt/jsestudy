package example.company.jse.fiddle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import example.company.tox.common.Common;

public class Fiddle19 {

	/* Inspect clientca.p12 */

	@Test
	public void fiddle() throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException,
			UnrecoverableKeyException {

		KeyStore ks1 = FiddleCommon.getKeyStore(FiddleCommon.CLIENT_JKS_7, "password");
		KeyStore ks2 = FiddleCommon.getKeyStore(FiddleCommon.CLIENTCA_P12_7, "password");

		Assert.assertTrue(ks2.containsAlias("clientca"));
		Assert.assertFalse(ks2.containsAlias("client"));

		PrivateKey pk1 = FiddleCommon.getPrivateKey(ks1, "clientca", "password");
		PrivateKey pk2 = FiddleCommon.getPrivateKey(ks2, "clientca", "password");
		Assert.assertArrayEquals(pk1.getEncoded(), pk2.getEncoded());

		X509Certificate x1 = FiddleCommon.getCertificate(ks1, "clientca");
		X509Certificate x2 = FiddleCommon.getCertificate(ks2, "clientca");
		Assert.assertArrayEquals(x1.getEncoded(), x2.getEncoded());

		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		IOUtils.copy(this.getClass().getResourceAsStream(FiddleCommon.CLIENTCA_P12_7), baos2);

		byte[] bytes2 = baos2.toByteArray();

		PKCS12Builder builder = new PKCS12Builder();

		Assert.assertArrayEquals(bytes2, builder.build());

	}

	@SuppressWarnings("unused")
	private void createAnotherPKCS12Store(Key pk1, X509Certificate x1)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore kout = KeyStore.getInstance("PKCS12");
		kout.load(null, "password".toCharArray());
		kout.setKeyEntry("clientca", pk1, "password".toCharArray(), new Certificate[] { x1 });
		ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
		kout.store(baos3, "password".toCharArray());
		byte[] bytes3 = baos3.toByteArray();
	}
}
