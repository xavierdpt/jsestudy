package example.company.jse.fiddle;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.AsnX509Interpretation;
import example.company.asn.utils.AsnX509InterpretationType;

public class Fiddle06 {

	/**
	 * Parse the TBS certificate as Asn, and interpret the Asn structure
	 * 
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws ParseException
	 */
	@Test
	public void fiddle()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, ParseException {

		char[] password = "password".toCharArray();

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/keystore1.jks");
		keystore.load(resourceAsStream, password);

		X509Certificate x = (X509Certificate) keystore.getCertificate("selfsigned");

		AsnElement tbs = AsnUtils.parse(x.getTBSCertificate());

		tbsTests(x, tbs);
	}

	public static void tbsTests(X509Certificate x, AsnElement tbs) {

		AsnX509Interpretation i = new AsnX509Interpretation(tbs, AsnX509InterpretationType.TBS);

		Assert.assertEquals(3, x.getVersion());
		Assert.assertEquals(3, i.getVersion());

		Assert.assertEquals(i.getSerialNumber(), x.getSerialNumber().longValue());

		Assert.assertEquals("1.2.840.113549.1.1.11", x.getSigAlgOID());
		Assert.assertEquals("1.2.840.113549.1.1.11", i.getSigAlgOID());

		Assert.assertEquals("SHA256withRSA", x.getSigAlgName());
		Assert.assertEquals("SHA256withRSA", i.getSigAlgName());

		Assert.assertEquals(x.getIssuerDN().getName(), i.getIssuerName());

		Assert.assertEquals(x.getNotBefore().getTime(), i.getNotBefore().getTime());
		Assert.assertEquals(x.getNotAfter().getTime(), i.getNotAfter().getTime());

		Assert.assertEquals(x.getSubjectDN().getName(), i.getSubjectName());

	}

}
