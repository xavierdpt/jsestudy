package example.company.jse.fiddle.java.security;

import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;

import org.junit.Test;

public class CertificateFactorySpiFiddle {

	@Test
	public void fiddle1() throws NoSuchAlgorithmException, CertificateException {

		Provider provider = Security.getProvider("SUN");
		Service service = provider.getService("CertificateFactory", "X.509");
		CertificateFactorySpi certificateFactorySpi = (CertificateFactorySpi) service.newInstance(null);

		Certificate certificate = certificateFactorySpi
				.engineGenerateCertificate(new ByteArrayInputStream(new byte[] {}));
	}
}
