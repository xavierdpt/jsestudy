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
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
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
import example.company.asn.utils.AsnCSRInterpretation;
import example.company.asn.utils.AsnObjectIdentifierUtils;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.AsnX509Interpretation;
import example.company.asn.utils.OIDS;

public class Fiddle11 {

	/**
	 * Analyze the certificate signing request client.crq in relation with the
	 * client certificate in client.jks
	 * 
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableKeyException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	@Test
	public void fiddle() throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
			UnrecoverableKeyException, InvalidKeyException, SignatureException {

		KeyStore keyStore = getKeyStore();
		X509Certificate x = getClientCertificate(keyStore);
		PrivateKey privateKey = getPrivateKey(keyStore);

		AsnElement csrAsn = getCSRAsn();

		AsnCSRInterpretation i = new AsnCSRInterpretation(csrAsn);

		testBasics(x, i);

		testPublicKey(x, i);

		testExtensions(x, i);

		testSignature(privateKey, csrAsn, i);
	}

	private void testBasics(X509Certificate x, AsnCSRInterpretation i) {
		Assert.assertEquals(0, i.getVersion());
		Assert.assertEquals(x.getSubjectDN().getName(), i.getSubjectName());
	}

	private void testPublicKey(Certificate x, AsnCSRInterpretation i) {
		PublicKey publicKey = x.getPublicKey();

		Assert.assertArrayEquals(publicKey.getEncoded(), i.getPublicKeyInfo().getEncoded());
		Assert.assertEquals(publicKey.getAlgorithm(), i.getPublicKeyInfo().getAlgorithmName());
	}

	private void testExtensions(X509Certificate x, AsnCSRInterpretation i) throws CertificateEncodingException {

		AsnElement additionalInfo = i.getAdditionalInfo();
		AsnSequence er = additionalInfo.asContextSpecific().getSequence();
		AsnSequence sk = er.getSet(1).getSequence(0).getSequence(0);

		Assert.assertEquals(0, additionalInfo.asContextSpecific().getTag());
		Assert.assertEquals(OIDS.EXTENSION_REQUEST_OID, er.getObjectIdentifier(0).getValue());
		Assert.assertEquals(OIDS.SUBJECT_KEY_IDENTIFIER_OID, sk.getObjectIdentifier(0).getValue());

		byte[] xtbs = x.getTBSCertificate();
		AsnElement asnTbs = AsnUtils.parse(xtbs);

		AsnX509Interpretation xi = new AsnX509Interpretation(asnTbs);

		AsnContextSpecific extensions = xi.getContextSpecific(3);
		AsnSequence xsk = extensions.getSequence().getSequence(0);
		Assert.assertEquals(xsk.getObjectIdentifier(0).getValue(), sk.getObjectIdentifier(0).getValue());
		Assert.assertArrayEquals(xsk.getOctetString(1).getValue(), sk.getOctetString(1).getValue());
	}

	private void testSignature(PrivateKey privateKey, AsnElement csrAsn, AsnCSRInterpretation i)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Assert.assertEquals(OIDS.SHA256RSA_OID, i.getSignatureOID());

		byte[] csrtbs = AsnUtils.encode(csrAsn.asSequence().getSequence(0));
		Signature sig = Signature.getInstance(AsnObjectIdentifierUtils.getLabel(i.getSignatureOID()));
		sig.initSign(privateKey);
		sig.update(csrtbs);
		byte[] signed = sig.sign();

		Assert.assertArrayEquals(i.getSignature(), signed);
	}

	private AsnElement getCSRAsn() throws IOException {
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

	private PrivateKey getPrivateKey(KeyStore keyStore)
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
		return (PrivateKey) keyStore.getKey("client", "password".toCharArray());
	}
}
