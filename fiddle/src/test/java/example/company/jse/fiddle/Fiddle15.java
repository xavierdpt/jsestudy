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

import example.company.asn.AsnEncoding;
import example.company.asn.elements.Asn;
import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.AsnX509Interpretation;
import example.company.asn.utils.AsnX509InterpretationType;
import example.company.asn.utils.X509Builder;
import example.company.tox.common.Common;

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

		b.setPublicKeyBSs(Common.bytes(
				"3082010A0282010100A5C0CF1C2B5AD83BAC210FC0B645FC25E1DCA8C4F048B963102773453C5732BC00A5C16D9681CBB89E2DAFEF0AA59AA97A110A9296CA998997DD611EA4410F3988B42A295B9263E330F7E770C4B927AACC08943EAA04CBCBE320031235904DABFDAA070B6F1A9C7220F08E5E43099A4BEAC2AABD47FFAF7F8DE344F1BDBF9E2BDD4D483449572BF1DF68BA954CBE1A2B47ACBF19182F52A05A15B8FFAB77CC9DB1D70D5C4B3308A2869DC33564BEE7F9A6213AA47750EA0105497681ACE3DFB6330E8C8729ECFD62CA39878EEFD6F3EDFF5EC3A1101900D43A67038702BD1B9D02058498E29B557A33376A59EFC437CAC1298C21A9E9B09C3887FEC6843226F30203010001"));

		b.setSerial(expectedCertificate.getSerialNumber().intValue());

		b.setNotBefore(expectedCertificate.getNotBefore());
		b.setNotAfter(expectedCertificate.getNotAfter());

		b.setAuthorityKeyIdentifier(crtI.getAuthorityKeyIdentifier());
		System.out.println(crtI.getExtKeyUsage());
		b.setExtKeyUsage(crtI.getExtKeyUsage());
		b.setSubjectKeyIdentifier(Common.bytes("0414A1613F82403D1E6A2478CABD8493AE96D63C9287"));

		b.setIssuerCC("US");
		b.setIssuerST("California");
		b.setIssuerL("San Francisco");
		b.setIssuerO("Example Company");
		b.setIssuerOU("Example Org");
		b.setIssuerCN("clientCA");
		b.setSubjectCC("US");
		b.setSubjectST("California");
		b.setSubjectL("San Francisco");
		b.setSubjectO("Example Company");
		b.setSubjectOU("Example Org");
		b.setSubjectCN("client");

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
