package example.company.jse.fiddle.java.security;

import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;

import org.junit.Test;

public class CertificateFactorySpiFiddle {

	@Test(expected=CertificateException.class)
	public void fiddle1() throws NoSuchAlgorithmException, CertificateException {

		Provider provider = Security.getProvider("SUN");
		Service service = provider.getService("CertificateFactory", "X.509");
		CertificateFactorySpi certificateFactorySpi = (CertificateFactorySpi) service.newInstance(null);

		certificateFactorySpi
				.engineGenerateCertificate(new ByteArrayInputStream(new byte[] {}));
	}
}
