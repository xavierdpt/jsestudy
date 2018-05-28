package example.company.jse.fiddle.java.security.cert;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import example.company.jse.something.java.security.KeyStoreSomething;

public class PKIXParametersFiddle {
	@Test(expected = InvalidAlgorithmParameterException.class)
	public void fiddle1() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			InvalidAlgorithmParameterException {
		KeyStore keyStore = KeyStoreSomething.createKeyStore();
		new PKIXParameters(keyStore);
	}

	@Test(expected = InvalidAlgorithmParameterException.class)
	public void fiddle2() throws InvalidAlgorithmParameterException {
		Set<TrustAnchor> trustAnchors = new HashSet<>();
		new PKIXParameters(trustAnchors);
	}
}
