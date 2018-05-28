package example.company.jse.fiddle;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnContextSpecific;
import example.company.asn.elements.AsnElement;
import example.company.asn.elements.AsnSequence;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.AsnX509Interpretation;
import example.company.asn.utils.CSRBuilder;

public class Fiddle12 {

	/**
	 * Generate certificate signing request with CSRBuilder from client certificate
	 * in client.jks and ensures it is the same as client.crq
	 * 
	 * @throws SignatureException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	@Test
	public void fiddle() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, KeyStoreException,
			CertificateException, IOException, UnrecoverableKeyException {

		KeyStore keyStore = getKeyStore();
		X509Certificate x = getClientCertificate(keyStore);
		PrivateKey privateKey = getPrivateKey(keyStore);

		byte[] csrBytes = getCSRBytes();

		CSRBuilder csrb = new CSRBuilder();
		csrb.setVersion(0);
		csrb.setSubjectDNName(x.getSubjectDN().getName());
		csrb.setPublickKey(x.getPublicKey());
		csrb.setSubjectKeyIdentifier(getSubjectKeyIdentifier(x));

		byte[] actualCsrBytes = csrb.encode(privateKey);

		Assert.assertArrayEquals(csrBytes, actualCsrBytes);

	}

	private byte[] getSubjectKeyIdentifier(X509Certificate x) throws CertificateEncodingException {
		byte[] xtbs = x.getTBSCertificate();
		AsnElement asnTbs = AsnUtils.parse(xtbs);
		AsnX509Interpretation xi = new AsnX509Interpretation(asnTbs);
		AsnContextSpecific extensions = xi.getContextSpecific(3);
		AsnSequence xsk = extensions.getSequence().getSequence(0);
		return xsk.getOctetString(1).getValue();
	}

	private byte[] getCSRBytes() throws IOException {
		InputStream r = this.getClass().getResourceAsStream("/genclient/step3/client.crq");
		Assert.assertNotNull(r);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(r, baos);
		byte[] bytes = baos.toByteArray();
		String str = new String(bytes, Charset.forName("UTF-8"));
		String[] lines = str.split("[\\r\\n]");
		StringWriter sw = new StringWriter();
		for (int i = 1; i < lines.length - 1; ++i) {
			sw.append(lines[i]);
		}
		return Base64.getDecoder().decode(sw.toString());
	}

	private KeyStore getKeyStore()
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(this.getClass().getResourceAsStream("/genclient/step3/client.jks"), "password".toCharArray());
		return keyStore;
	}

	private X509Certificate getClientCertificate(KeyStore keyStore) throws KeyStoreException {
		return (X509Certificate) keyStore.getCertificate("client");
	}

	private PrivateKey getPrivateKey(KeyStore keyStore)
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (PrivateKey) keyStore.getKey("client", "password".toCharArray());
	}
}
