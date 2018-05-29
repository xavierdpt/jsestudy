package example.company.jse.fiddle;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.junit.Assert;
import org.junit.Test;

import example.company.asn.elements.AsnElement;
import example.company.asn.utils.AsnUtils;
import example.company.asn.utils.AsnX509Interpretation;
import example.company.asn.utils.AsnX509InterpretationType;
import example.company.asn.utils.OIDS;
import example.company.tox.asn.AsnTox;
import example.company.tox.common.Common;
import example.company.tox.common.Tox;

public class Fiddle14 {

	/** Inspect client.crt file **/

	@Test
	public void fiddle() throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
			UnrecoverableKeyException, InvalidKeyException, SignatureException {

		KeyStore keyStore = FiddleCommon.getKeyStore("/genclient/step4/client.jks", "password");
		X509Certificate cx = FiddleCommon.getCertificate(keyStore, "client");
		X509Certificate cax = FiddleCommon.getCertificate(keyStore, "clientCA");
		AsnElement cxFullAsn = AsnUtils.parse(cx.getEncoded());
		AsnElement caxFullAsn = AsnUtils.parse(cax.getEncoded());
		AsnX509Interpretation cxi = new AsnX509Interpretation(cxFullAsn, AsnX509InterpretationType.FULL);
		AsnX509Interpretation caxi = new AsnX509Interpretation(caxFullAsn, AsnX509InterpretationType.FULL);

		byte[] csrBytes = FiddleCommon.getCertificateSigningRequestBytes("/genclient/step4/client.crq");
		AsnElement csrAsn = AsnUtils.parse(csrBytes);

		byte[] crtBytes = FiddleCommon.getCertificateBytes("/genclient/step4/client.crt");
		AsnElement crtAsn = AsnUtils.parse(crtBytes);

		Signature sig = Signature.getInstance(cx.getSigAlgName());
		sig.initVerify(cx.getPublicKey());
		sig.update(AsnUtils.encode(csrAsn.asSequence().get(0)));
		sig.verify(csrAsn.asSequence().getBitString(2).toByteArray());

		System.out.println(Common.bytesToString(crtBytes));
		Tox.print(new AsnTox().tox(crtAsn), System.out);

		AsnX509Interpretation crtTbsI = new AsnX509Interpretation(crtAsn, AsnX509InterpretationType.FULL);

		/* Inspect certificate */

		// Version is (almost) always 3
		Assert.assertEquals(3, crtTbsI.getVersion());

		// Serial number is new (i.e. not client's and not clientCA's)
		long crtSN = crtTbsI.getSerialNumber();
		Assert.assertNotEquals(cx.getSerialNumber(), crtSN);
		Assert.assertNotEquals(cax.getSerialNumber(), crtSN);

		// Signature algorithm does not change
		String crtSigOID = crtTbsI.getSigAlgOID();
		Assert.assertEquals(cx.getSigAlgOID(), crtSigOID);
		Assert.assertEquals(cax.getSigAlgOID(), crtSigOID);

		// Subject and issuer are the same in both self-signed certificates, but
		// different in each certificate

		String caSubject = cax.getSubjectDN().getName();
		String caIssuer = cax.getIssuerDN().getName();

		String cSubject = cx.getSubjectDN().getName();
		String cIssuer = cx.getIssuerDN().getName();

		Assert.assertEquals(caSubject, caIssuer);
		Assert.assertEquals(cSubject, cIssuer);
		Assert.assertNotEquals(caSubject, cSubject);

		// CRT subject is client and issuer is CA
		String crtSubject = crtTbsI.getSubjectName();
		String crtIssuer = crtTbsI.getIssuerName();

		Assert.assertEquals(cSubject, crtSubject);
		Assert.assertEquals(caSubject, crtIssuer);

		// Times are set anew each time
		Assert.assertNotNull(crtTbsI.getNotBefore());
		Assert.assertNotNull(crtTbsI.getNotAfter());

		Assert.assertEquals(cx.getPublicKey().getAlgorithm(), crtTbsI.getPublicKeyAlgorithm());
		Assert.assertArrayEquals(cx.getPublicKey().getEncoded(), crtTbsI.getPublicKeyEncoded());

		// Subject key identifier is that of the client
		Assert.assertArrayEquals(cxi.getSubjectKeyIdentifier(), crtTbsI.getSubjectKeyIdentifier());

		// Authority key identifier is subject key identifier of the CA
		Assert.assertArrayEquals(caxi.getSubjectKeyIdentifier(), crtTbsI.getAuthorityKeyIdentifier());

		// Certificate as Client Auth Ext Key Usage
		Assert.assertEquals(OIDS.CLIENT_AUTH, crtTbsI.getExtKeyUsage());

		// Verify the signature

		Signature s = Signature.getInstance(cx.getSigAlgName());
		s.initSign(FiddleCommon.getPrivateKey(keyStore, "clientCA", "password"));
		s.update(crtTbsI.getTbs().encode());
		byte[] actualSignature = s.sign();

		Assert.assertArrayEquals(crtTbsI.getSignature(), actualSignature);

		// TODO (next Fiddle) : generate this certificate from the others

	}

}
