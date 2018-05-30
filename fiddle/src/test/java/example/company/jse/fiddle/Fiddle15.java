package example.company.jse.fiddle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.AsnX509Interpretation;
import example.company.asn.utils.AsnX509InterpretationType;
import example.company.asn.utils.X509Builder;

public class Fiddle15 {

	/* Generate client.crt without keytool */

	@Test
	public void fiddle() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException,
			UnrecoverableKeyException, InvalidKeyException, SignatureException {

		KeyStore keyStore = FiddleCommon.getKeyStore("/genclient/step4/client.jks", "password");

		String crtPath = "/genclient/step4/client.crt";
		byte[] expectedCrtBytes = FiddleCommon.getCertificateBytes(crtPath);
		AsnElement crtAsn = AsnUtils.parse(expectedCrtBytes);
		AsnX509Interpretation crtI = new AsnX509Interpretation(crtAsn, AsnX509InterpretationType.FULL);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate expectedCertificate = (X509Certificate) cf
				.generateCertificate(new ByteArrayInputStream(expectedCrtBytes));

		PrivateKey privateKey = FiddleCommon.getPrivateKey(keyStore, "clientCA", "password");

		X509Builder b = new X509Builder();

		b.setEncodedPublicKey(expectedCertificate.getPublicKey().getEncoded());

		b.setSerial(expectedCertificate.getSerialNumber().intValue());

		b.setNotBefore(expectedCertificate.getNotBefore());
		b.setNotAfter(expectedCertificate.getNotAfter());

		b.setAuthorityKeyIdentifier(crtI.getAuthorityKeyIdentifier());
		b.setExtKeyUsage(crtI.getExtKeyUsage());
		b.setSubjectKeyIdentifier(crtI.getSubjectKeyIdentifier());

		b.setIssuerName(crtI.getIssuerName());
		b.setSubjectName(crtI.getSubjectName());

		byte[] actualCrtBytes = b.encode(privateKey);

		Assert.assertArrayEquals(expectedCrtBytes, actualCrtBytes);

		testB64(crtPath, actualCrtBytes);

	}

	private void testB64(String crtPath, byte[] actualCrtBytes) throws IOException {
		String expectedCrtString = FiddleCommon.getResourceContent(crtPath);

		String b64 = Base64.getEncoder().encodeToString(actualCrtBytes);

		StringWriter sw = new StringWriter();
		sw.append("-----BEGIN CERTIFICATE-----");
		sw.append("\r\n");
		for (int i = 0; i < b64.length(); i += 64) {
			int e = i + 64;
			if (e > b64.length()) {
				e = b64.length();
			}
			sw.append(b64.substring(i, e));
			sw.append("\r\n");
		}
		sw.append("-----END CERTIFICATE-----");
		sw.append("\r\n");

		String actualCrtString = sw.toString();

		Assert.assertEquals(expectedCrtString, actualCrtString);
	}
}
