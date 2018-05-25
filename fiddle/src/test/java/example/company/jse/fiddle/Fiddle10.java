package example.company.jse.fiddle;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import example.company.asn.elements.AsnBitString;
import example.company.asn.elements.AsnContextSpecific;
import example.company.asn.elements.AsnElement;
import example.company.asn.elements.AsnObjectIdentifier;
import example.company.asn.elements.AsnOctetString;
import example.company.asn.elements.AsnSequence;
import example.company.asn.utils.AsnObjectIdentifierUtils;
import example.company.asn.utils.AsnUtils;
import example.company.tox.asn.AsnTox;
import example.company.tox.common.Common;
import example.company.tox.common.Tox;

/*
 * 
 */
public class Fiddle10 {

	private char[] password = "password".toCharArray();
	private String caAlias = "clientCA";

	@Test
	public void test() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException {

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream resourceAsStream = this.getClass().getResourceAsStream("/genclient/step1/client.jks");
		keystore.load(resourceAsStream, password);

		X509Certificate x = (X509Certificate) keystore.getCertificate(caAlias);
		Assert.assertNotNull(x);

		RSAPublicKey pub = (RSAPublicKey) x.getPublicKey();
		Assert.assertNotNull(pub);

		RSAPrivateCrtKey priv = (RSAPrivateCrtKey) keystore.getKey(caAlias, password);
		Assert.assertNotNull(priv);

		System.out.println(x.getSigAlgName());

		Assert.assertEquals("RSA", priv.getAlgorithm());
		Assert.assertEquals("RSA", pub.getAlgorithm());

		System.out.println("Is CA ? " + (x.getBasicConstraints() != -1 ? "yes" : "no"));

		Document document = Tox.createDocument();
		for (String nceoid : x.getNonCriticalExtensionOIDs()) {
			System.out.println(AsnObjectIdentifierUtils.getLabel(nceoid));
			byte[] ev = x.getExtensionValue(nceoid);
			System.out.println(Common.bytesToString(ev));
			Element e = Tox.appendChild(document, document.getDocumentElement(), "youpi");
			AsnOctetString asn = (AsnOctetString) AsnUtils.parse(ev);

			AsnTox.marshal(document, e, AsnUtils.parse(asn.getValue()));
		}

		AsnElement tbsAsn = AsnUtils.parse(x.getTBSCertificate());

		new AsnTox().tox(document, "tbs", tbsAsn);

		Tox.print(document, System.out);

		System.out.println(Common.booleansToString(x.getKeyUsage()));
		Assert.assertEquals(true, x.getKeyUsage()[5]);

		// Assert.assertTrue(AsnInterpretor.isKeyCertSign(tbsAsn));

		AsnSequence asnExts = tbsAsn.as(AsnSequence.class).getElements().get(7).as(AsnContextSpecific.class)
				.getElement().as(AsnSequence.class);

		asnExts.getElements().forEach(element -> {
			List<AsnElement> extSequenceElements = element.as(AsnSequence.class).getElements();
			if ("2.5.29.15".equals(extSequenceElements.get(0).as(AsnObjectIdentifier.class).getValue())) {
				AsnElement youpi = AsnUtils.parse(extSequenceElements.get(1).as(AsnOctetString.class).getValue());
				Tox.print(new AsnTox().tox(youpi), System.out);
				boolean good = (0x8 == (youpi.as(AsnBitString.class).toByteArray()[0] & 0x8));
				System.out.println(Common.bytesToString(youpi.as(AsnBitString.class).toByteArray()));
			}
		});

	}

}
