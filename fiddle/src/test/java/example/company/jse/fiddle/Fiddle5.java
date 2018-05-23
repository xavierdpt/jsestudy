package example.company.jse.fiddle;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnUtils;
import example.company.tox.common.Bytes;

public class Fiddle5 {

	/**
	 * Parse the certificate as Asn, then encoded the Asn back into bytes and check
	 * that the signature on the generated bytes is the same
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnrecoverableKeyException 
	 * @throws InvalidKeyException 
	 * @throws SignatureException 
	 * 
	 */
	@Test
	public void fiddle() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, InvalidKeyException, SignatureException {

		char[] password = "password".toCharArray();

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/keystore1.jks");
		keystore.load(resourceAsStream, password);

		PrivateKey privateKey = (PrivateKey) keystore.getKey("selfsigned", password);

		X509Certificate certificate = (X509Certificate) keystore.getCertificate("selfsigned");
		byte[] encoded = certificate.getEncoded();
		AsnElement asn = AsnUtils.parse(new Bytes(encoded));

		byte[] bytes = AsnUtils.encode(asn);

		Signature sig = Signature.getInstance("SHA256withRSA");

		sig.initSign(privateKey);
		sig.update(bytes);
		byte[] actual = sig.sign();

		Assert.assertArrayEquals(certificate.getSignature(), actual);

	}

}