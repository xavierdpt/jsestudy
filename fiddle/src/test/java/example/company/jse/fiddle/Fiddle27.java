package example.company.jse.fiddle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.junit.Test;

import example.company.tox.asn.AsnTox;
import example.company.tox.common.Tox;
import xdptdr.asn.utils.AsnUtils;

public class Fiddle27 {
	@Test
	public void fiddle() throws IOException, CertificateException {
		byte[] bytes = FiddleCommon.getResourceBytes(FiddleCommon.LETSENCRTYPT);
		Tox.print(new AsnTox().tox(AsnUtils.parse(bytes)), System.out);

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate x = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(bytes));
		System.out.println(x.getSerialNumber());
		System.out.println(x.getIssuerDN().getName());
		System.out.println(x.getSubjectDN().getName());
	}
}
