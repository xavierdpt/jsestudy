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
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import xdptdr.asn.elements.AsnElement;
import xdptdr.asn.utils.AsnUtils;

public class Fiddle13 {
	
	/**
	 * Verify CSR signature
	 */

	@Test
	public void fiddle() throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
			UnrecoverableKeyException, InvalidKeyException, SignatureException {

		KeyStore keyStore = getKeyStore();
		X509Certificate x = getClientCertificate(keyStore);

		AsnElement csrAsn = getCertificateSigningRequestAsn();

		Signature sig = Signature.getInstance(x.getSigAlgName());
		sig.initVerify(x.getPublicKey());
		sig.update(AsnUtils.encode(csrAsn.asSequence().get(0)));
		sig.verify(csrAsn.asSequence().getBitString(2).toByteArray());

	}

	private AsnElement getCertificateSigningRequestAsn() throws IOException {
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
		byte[] d = Base64.getDecoder().decode(sw.toString());
		return AsnUtils.parse(d);

	}

	private KeyStore getKeyStore()
			throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
		keyStore.load(this.getClass().getResourceAsStream("/genclient/step3/client.jks"), "password".toCharArray());
		return keyStore;
	}

	private X509Certificate getClientCertificate(KeyStore keyStore) throws KeyStoreException {
		return (X509Certificate) keyStore.getCertificate("client");
	}

}