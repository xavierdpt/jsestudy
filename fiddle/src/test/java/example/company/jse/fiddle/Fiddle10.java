package example.company.jse.fiddle;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.AsnX509Interpretation;
import example.company.tox.asn.AsnTox;
import example.company.tox.common.Tox;

/*
 * Check that certificate clientCA in genclient step 1 has keyCertSign key usage
 */
public class Fiddle10 {

	private char[] password = "password".toCharArray();
	private String caAlias = "clientCA";

	@Test
	public void fiddle() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/genclient/step1/client.jks");
		keystore.load(resourceAsStream, password);

		X509Certificate x = (X509Certificate) keystore.getCertificate(caAlias);
		Assert.assertNotNull(x);

		AsnElement tbsAsn = AsnUtils.parse(x.getTBSCertificate());
		AsnX509Interpretation i = new AsnX509Interpretation(tbsAsn);

		Assert.assertEquals(true, x.getKeyUsage()[5]);
		Assert.assertTrue(i.isKeyCertSign());
		Assert.assertArrayEquals(i.getKeyUsage(), x.getKeyUsage());

		Tox.print(new AsnTox().tox(tbsAsn), System.out);

		Assert.assertEquals(i.getBasicConstraints(), x.getBasicConstraints());
	}

}
