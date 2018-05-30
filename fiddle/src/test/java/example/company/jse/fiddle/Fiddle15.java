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
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.utils.CertificateCreator;

public class Fiddle15 {

	/* Generate client.crt without keytool */

	@Test
	public void fiddle() throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException,
			UnrecoverableKeyException, InvalidKeyException, SignatureException {

		// The client and CA certificate and CA's private key are required
		KeyStore keyStore = FiddleCommon.getKeyStore("/genclient/step4/client.jks", "password");

		X509Certificate clientCertificate = FiddleCommon.getCertificate(keyStore, "client");
		X509Certificate caCertificate = FiddleCommon.getCertificate(keyStore, "clientCA");

		PrivateKey caPrivateKey = FiddleCommon.getPrivateKey(keyStore, "clientCA", "password");

		// Retrieve the expected output
		InputAndExpectedOutput iaeo = getInputAndExpectedOutput();

		// These input parameters are usually autogenerated ; they are retrieved from
		// the expected output
		int serialNumber = iaeo.serialNumber;
		Date notBefore = iaeo.notBefore;
		Date notAfter = iaeo.notAfter;

		// Create the client certificate signed with the CA's private key
		byte[] actualBytes = CertificateCreator.signCertificateWithAuthority(clientCertificate, caCertificate,
				serialNumber, notBefore, notAfter, caPrivateKey);

		// Check that the bytes match
		Assert.assertArrayEquals(iaeo.expectedBytes, actualBytes);

		// Bonus base64 check
		testB64(actualBytes);

	}

	private static class InputAndExpectedOutput {
		public byte[] expectedBytes;
		public int serialNumber;
		public Date notBefore;
		public Date notAfter;
	}

	private InputAndExpectedOutput getInputAndExpectedOutput() throws IOException, CertificateException {

		byte[] expectedBytes = FiddleCommon.getCertificateBytes("/genclient/step4/client.crt");

		X509Certificate x = (X509Certificate) CertificateFactory.getInstance("X.509")
				.generateCertificate(new ByteArrayInputStream(expectedBytes));

		InputAndExpectedOutput expectedStuff = new InputAndExpectedOutput();
		expectedStuff.expectedBytes = expectedBytes;
		expectedStuff.serialNumber = x.getSerialNumber().intValue();
		expectedStuff.notBefore = x.getNotBefore();
		expectedStuff.notAfter = x.getNotAfter();
		return expectedStuff;
	}

	private void testB64(byte[] actualCrtBytes) throws IOException {
		String expectedCrtString = FiddleCommon.getResourceContent("/genclient/step4/client.crt");

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
