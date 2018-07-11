package example.company.jse.fiddle;

import java.io.IOException;
import java.net.ConnectException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Test;

import example.company.tox.asn.AsnTox;
import example.company.tox.common.Tox;
import xdptdr.asn.utils.AsnUtils;

public class Fiddle24 {

	private String hostname = "";

	/* Use this fiddle to retrieve a certificate from an HTTPS website */

	@Test(expected = ConnectException.class)
	public void fiddle() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException,
			CertificateException, IOException, UnrecoverableKeyException {

		X509Certificate certificate = CertificateRetriever.getCertificate(hostname);

		Tox.print(new AsnTox().tox(AsnUtils.parse(certificate.getEncoded())), System.out);

	}

}
