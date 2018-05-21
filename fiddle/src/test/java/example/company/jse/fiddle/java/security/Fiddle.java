package example.company.jse.fiddle.java.security;

import java.security.Certificate;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathParameters;
import java.security.cert.CertSelector;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import org.junit.Test;

public class Fiddle {
	@Test
	public void fiddle1()
			throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, CertPathBuilderException, KeyStoreException {
		Certificate.class.getName();
		java.security.cert.Certificate.class.getName();
		javax.security.cert.Certificate.class.getName();

		X509Certificate.class.getName();
		javax.security.cert.X509Certificate.class.getName();

		Provider provider = Security.getProvider("SUN");
		Service service = provider.getService("CertPathBuilder", "PKIX");
		CertPathBuilderSpi certPathBuilderSpi = (CertPathBuilderSpi) service.newInstance(null);

		KeyStore keystore = null;
		CertSelector targetConstraints = new X509CertSelector();
		CertPathParameters params = new PKIXBuilderParameters(keystore, targetConstraints);
		CertPathBuilderResult certPathBuilderResult = certPathBuilderSpi.engineBuild(params);

		new PKIXParameters(keystore);

	}
}
