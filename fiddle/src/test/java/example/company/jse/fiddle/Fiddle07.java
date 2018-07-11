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

import xdptdr.asn.elements.AsnElement;
import xdptdr.asn.elements.AsnSequence;
import xdptdr.asn.interpretations.AsnX509Interpretation;
import xdptdr.asn.interpretations.AsnX509InterpretationType;
import xdptdr.asn.utils.AsnUtils;

public class Fiddle07 {

	/**
	 * Parse the Certificate as Asn, and use the interpretor to get the signature
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

		AsnElement root = AsnUtils.parse(x.getEncoded());
		AsnElement tbs = root.as(AsnSequence.class).getElements().get(0);

		Fiddle06.tbsTests(x, tbs);

		AsnX509Interpretation i = new AsnX509Interpretation(root, AsnX509InterpretationType.FULL);

		Assert.assertArrayEquals(x.getSignature(), i.getSignature());
	}

}
